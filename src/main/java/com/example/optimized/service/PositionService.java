package com.example.optimized.service;

import com.example.optimized.model.Position;
import com.example.optimized.repository.PositionRepository;
import com.example.optimized.repository.PositionRepositoryBulk;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository repository;
    private final PositionRepositoryBulk repositoryBulk;

    public List<Position> get(Position example, Pageable pageable) {
        return repository.findAll(Example.of(example), pageable).getContent();
    }

    public List<Position> get(Iterable<String> account) {
        return repository.findAllByAccountIn(account);
    }

    public Position save(Position position) {
        return repository.save(position);
    }

    public void saveAll(Iterable<Position> positions) {
        repositoryBulk.saveAll(positions);
    }

    public void insert(Iterable<Position> positions){
        repositoryBulk.insert(positions);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteAllByAccountIn(Iterable<String> accounts){
        repository.deleteAllByAccountIn(accounts);
    }
}
