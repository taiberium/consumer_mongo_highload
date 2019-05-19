package com.example.optimized.repository;

import com.example.optimized.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PositionRepository extends MongoRepository<Position, String> {

    List<Position> findAllByAccountIn(Iterable<String> accounts);

    void deleteAllByAccountIn(Iterable<String> accounts);
}
