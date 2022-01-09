package com.example.DemoTest.core.auth;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class Login {
    @NotBlank
    private  String userName;
    @NotBlank
    private  String passWord;
}
