package com.example.DemoTest.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class GameDTO {
    Long id;
    String name;
    String image;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
