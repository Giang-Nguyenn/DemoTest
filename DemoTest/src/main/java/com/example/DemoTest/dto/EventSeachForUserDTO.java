package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.StatusEvent;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EventSeachForUserDTO {
    Long id;
    Long game_id;
    StatusEvent status;
    LocalDateTime startAt;
}
