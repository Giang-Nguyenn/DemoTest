package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.StatusEvent;
import com.example.DemoTest.model.UserGame;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EventDTO {
    Long id;
    UserGameDTO userGame;
    StatusEvent status;
    LocalDateTime startAt;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
