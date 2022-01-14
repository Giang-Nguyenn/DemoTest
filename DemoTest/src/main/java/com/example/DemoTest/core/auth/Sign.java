package com.example.DemoTest.core.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
public class Sign {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{6,15}$",message = "username is not valid")
    String username;
    @Pattern(regexp = "^[a-z0-9]{6,20}$")
    @NotBlank
    String password;
}
