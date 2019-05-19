package com.example.optimized.service;

import com.example.optimized.config.PositionBinding;
import com.example.optimized.model.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PositionListener {

    private final PositionService service;

    @StreamListener(PositionBinding.POSITIONS)
    public void processPositions(List<Position> newPositions) {
        Counter.increment();
        var accountSet = newPositions.stream().map(Position::getAccount).collect(Collectors.toSet());
        var oldPositions = service.get(accountSet);
        logData("Before", oldPositions);

        var updatedPositions = updatePositionIds(oldPositions, newPositions);

        service.deleteAllByAccountIn(accountSet);
        service.insert(updatedPositions);
        logData("After", updatedPositions);
    }

    private void logData(String prefix, List<Position> positions) {
        log.info("{}: {}", prefix, positions);
    }

    private List<Position> updatePositionIds(List<Position> oldPositions, List<Position> newPositions) {
        var oldPositionMap = oldPositions.stream().collect(Collectors.toMap(Position::getIsin, position -> position, (p1, p2) -> p1));

        return newPositions.stream()
                .peek(position -> {
                    var oldPosition = oldPositionMap.get(position.getIsin());
                    if (oldPosition != null) {
                        position.setId(oldPosition.getId());
                    }
                })
                .collect(Collectors.toList());
    }
}
