package com.example.DemoTest.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventSeachForGameDTO {
    Long id;
    Long user_id;
    String status;
    LocalDateTime startAt;
}
