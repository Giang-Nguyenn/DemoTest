package com.example.DemoTest.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountUserAndGameAboutTimeDTO {
    Long user;
    Long game;
}
