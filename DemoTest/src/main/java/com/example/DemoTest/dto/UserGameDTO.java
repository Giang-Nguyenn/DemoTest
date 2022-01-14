package com.example.DemoTest.dto;

import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserGameDTO {
    Long id;
    UserDTOBasic user;
    Game game;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
