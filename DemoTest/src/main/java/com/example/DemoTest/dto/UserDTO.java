package com.example.DemoTest.dto;

import com.example.DemoTest.core.penum.Gender;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String email;
    private String fullName;
    private String phone;
    private Gender gender;
    private String image;
    private String note;
    private boolean isActivity;
    private LocalDateTime createAt;
}
