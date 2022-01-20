package com.example.DemoTest.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventForManyUserDTO {
    Long userId;
    Long sumEvent;
}
