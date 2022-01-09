package com.example.DemoTest.controller.auth;

import com.example.DemoTest.core.auth.Login;
import com.example.DemoTest.core.auth.LoginResponse;
import com.example.DemoTest.core.auth.Sign;
import com.example.DemoTest.core.auth.jwt.JwtTokenProvider;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.CustomUserDetails;
import com.example.DemoTest.model.User;
import com.example.DemoTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignLogin {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @GetMapping("/test")
    String test(){
        System.out.println("test");
        return "test success";
    }
    @PostMapping("/sign")
    public ResponseEntity<UserDTO> sign(@Validated @RequestBody Sign sign){
        User user = userService.saveUserSign(sign);
        return new ResponseEntity<>(IUserMapper.INSTANCE.userToDTO(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public  LoginResponse login(@RequestBody Login loginRequest){
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassWord()
                )
        );
        System.out.println(authentication);
//        System.out.println(authentication.getAuthorities());

//        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(authentication.getPrincipal().toString());
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        System.out.println(jwt.toString());
        return new LoginResponse(jwt);
//        return "ok";
    }
}
