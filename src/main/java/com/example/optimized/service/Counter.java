package com.example.optimized.service;

import java.util.concurrent.atomic.LongAdder;

public class Counter {

    private static final LongAdder COUNTER = new LongAdder();

    public static void increment(){
        COUNTER.increment();
    }

    public static void reset(){
        COUNTER.reset();
    }

    public static long get(){
        return COUNTER.sum();
    }
}
