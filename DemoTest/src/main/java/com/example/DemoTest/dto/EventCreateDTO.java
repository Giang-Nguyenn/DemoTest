package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.StatusEvent;
import lombok.Data;

@Data
public class EventCreateDTO {
    Long userGameId;
    StatusEvent status;
}
