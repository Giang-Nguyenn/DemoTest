package com.example.DemoTest.dto.statistic;

import java.sql.Date;
import java.time.LocalDateTime;

public interface IGameEventByDate {
    Date getDate();
    Long getSumEvent();
}
