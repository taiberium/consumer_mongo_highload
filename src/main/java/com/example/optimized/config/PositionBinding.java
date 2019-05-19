package com.example.optimized.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface PositionBinding {

    String POSITIONS = "positions";

    @Input(POSITIONS)
    MessageChannel positions();
}
