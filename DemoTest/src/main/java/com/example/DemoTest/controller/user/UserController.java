package com.example.DemoTest.controller.user;

import com.example.DemoTest.core.UploadFile;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.User;
import com.example.DemoTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("user")
    public List<UserDTO> listUser(Pageable pageable){
        System.out.println(pageable.toString());
        Page<User> listUsers = userService.findAll(pageable);
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user:listUsers){
            userDTOS.add(IUserMapper.INSTANCE.userToDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/user/{id}")
    public UserDTO retrieveUser(@PathVariable Long id){
        return IUserMapper.INSTANCE.userToDTO(userService.findUserById(id));
    }

    @PatchMapping("/user/{id}")
    public UserDTO partialUpdate(@PathVariable Long id, @RequestBody Map<Object,Object> fields){

        System.out.println(fields);
        User user = userService.findUserById(id);
        Field[] fields1 = User.class.getFields(); // returns inherited members but not private members.
        Field[] fields2 = User.class.getDeclaredFields();
        System.out.println(fields1.length);
        System.out.println(fields2[1]);
        fields.remove("id");
        fields.forEach((k,v)->{
            Field field= ReflectionUtils.findField(User.class,(String) k);
            field.setAccessible(true);
            System.out.println(field);
            ReflectionUtils.setField(field,user,v);
        });
        System.out.println(user);
        UserDTO userDTO=IUserMapper.INSTANCE.userToDTO(userService.save(user));
        return userDTO;

    }

    @DeleteMapping("/user/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        Boolean status = userService.delete(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    ResponseEntity<String> uploadImage(@PathVariable Long id, MultipartFile file) throws IOException {
        String url = userService.uploadImage(id,file);
        return  new ResponseEntity<>("url :" + url ,HttpStatus.OK);
    }
}
