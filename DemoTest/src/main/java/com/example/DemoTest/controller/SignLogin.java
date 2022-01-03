package com.example.DemoTest.controller;

import com.example.DemoTest.core.Sign;
import com.example.DemoTest.repository.IUserRepository;
import com.example.DemoTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SignLogin {
    @Autowired
    UserService userService;
    @GetMapping("/test")
    String test(){
        System.out.println("test");
        return "test success";
    }
    @PostMapping("/sign")
    public  String sign(@Validated @RequestBody Sign sign) throws Exception {
        System.out.println(sign.toString());
        String s = userService.saveUserSign(sign);
        return "sign";
    }
}
