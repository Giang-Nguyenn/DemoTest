package com.example.DemoTest.core;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Data
public class Sign {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{5,29}$",message = "userName is not valid")
    String userName;
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,}$")
    @NotBlank
    String passWord;
}
