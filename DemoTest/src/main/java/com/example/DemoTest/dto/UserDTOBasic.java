package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTOBasic {
    Long id;
    private String username;
    private String fullName;
    private String phone;
    private String image;
}
