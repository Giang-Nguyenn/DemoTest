package com.example.DemoTest.dto;

import com.example.DemoTest.model.UserGame;
import lombok.Data;

import java.util.Date;

@Data
public class EventDTO {
    Long id;
    UserGame userGame;
    Date startTime;
}
