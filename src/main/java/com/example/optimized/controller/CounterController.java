package com.example.optimized.controller;

import com.example.optimized.service.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("counter")
public class CounterController {


    @PostMapping
    public void increment() {
        Counter.increment();
    }

    @GetMapping
    public long get(){
        return Counter.get();
    }

    @DeleteMapping
    public void reset() {
        Counter.reset();
    }

}
