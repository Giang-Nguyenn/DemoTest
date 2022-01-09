package com.example.DemoTest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GameDTO {
    Long id;
    String name;
    Date createAt;
    Date updateAt;
}
