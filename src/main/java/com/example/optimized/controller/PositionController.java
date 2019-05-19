package com.example.optimized.controller;

import com.example.optimized.model.Position;
import com.example.optimized.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("positions")
public class PositionController {

    private final PositionService service;

    @GetMapping
    public List<Position> get(
            Position position,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.get(position, PageRequest.of(page, size));
    }

    @PostMapping
    public Position get(
            @RequestBody Position position
    ) {
        return service.save(position);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

}
