package com.example.DemoTest.controller;

import com.example.DemoTest.core.FiterPermissionUpdate;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.exception.UnauthorizedException;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.User;
import com.example.DemoTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FiterPermissionUpdate permissionUpdate;

    @GetMapping("user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> listUser(Pageable pageable){
        System.out.println(pageable.toString());
        List<User> listUsers = userService.findAllUser(pageable);
        List<UserDTO> userDTOS=IUserMapper.INSTANCE.userToListDTO(listUsers);
        return userDTOS;
    }

    @GetMapping("/user/{id}")
    public UserDTO retrieveUser(@PathVariable Long id){
        return IUserMapper.INSTANCE.userToDTO(userService.findUserById(id));
    }

    @PatchMapping("/user/{id}")
    public UserDTO partialUpdate(@PathVariable Long id, @RequestBody Map<Object,Object> fields){
        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
            throw new UnauthorizedException("Unauthorized update with user id : "+id);
        User user = userService.findUserById(id);
        fields.remove("id");
        fields.remove("userName");
        fields.remove("passWord");
        fields.forEach((k,v)->{
            Field field= ReflectionUtils.findField(User.class,(String) k);
            field.setAccessible(true);
            System.out.println(field);
            ReflectionUtils.setField(field,user,v);
        });
        UserDTO userDTO=IUserMapper.INSTANCE.userToDTO(userService.save(user));
        return userDTO;

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        Boolean status = userService.delete(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/user/{id}")
    ResponseEntity<String> uploadImage(@PathVariable Long id, MultipartFile file) throws IOException {
        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
            throw new UnauthorizedException("Unauthorized update with user id : "+id);
        String url = userService.uploadImage(id,file);
        return  new ResponseEntity<>("url :" + url ,HttpStatus.OK);
    }
    @PostMapping("/user/change_password/{id}")
    ResponseEntity<String> changePassword(@PathVariable Long id) {
//        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
//            throw new UnauthorizedException("Unauthorized update with user id : "+id);
//        ipm late
        return  new ResponseEntity<>("success"  ,HttpStatus.OK);
    }
}
