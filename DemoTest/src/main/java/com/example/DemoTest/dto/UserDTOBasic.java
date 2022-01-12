package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.Gender;
import lombok.Data;

@Data
public class UserDTOBasic {
    Long id;
    private String userName;
    private String fullName;
    private String phone;
    private Gender gender;
}
