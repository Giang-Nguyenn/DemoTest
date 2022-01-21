package com.example.DemoTest.controller;

import com.example.DemoTest.core.sercurity.FiterPermissionUpdate;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.exception.UnauthorizedException;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.User;
import com.example.DemoTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
//    @Autowired
    private final UserService userService;
//    @Autowired
    private final FiterPermissionUpdate permissionUpdate;

    @GetMapping("user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> listUser(Pageable pageable){
        System.out.println(pageable.toString());
        List<User> listUsers = userService.findAllUser(pageable);
        List<UserDTO> userDTOS=IUserMapper.INSTANCE.userToListDTO(listUsers);
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> retrieveUser(@PathVariable Long id){
        System.out.println(userService.findUserById(id));
        return new ResponseEntity<>(IUserMapper.INSTANCE.userToDTO(userService.findUserById(id)),HttpStatus.OK);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDTO> partialUpdate(@PathVariable Long id, @RequestBody Map<Object,Object> fields){
        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
            throw new UnauthorizedException("Unauthorized update with user id : "+id);
        User user = userService.findUserById(id);
        fields.remove("id");
        fields.remove("username");
        fields.remove("password");
        fields.forEach((k,v)->{
            Field field= ReflectionUtils.findField(User.class,(String) k);
            field.setAccessible(true);
            System.out.println(field);
            ReflectionUtils.setField(field,user,v);
        });
        UserDTO userDTO=IUserMapper.INSTANCE.userToDTO(userService.save(user));
        return new ResponseEntity<>(userDTO,HttpStatus.PARTIAL_CONTENT);

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    ResponseEntity<String> delete(@PathVariable Long id){
        Boolean status = userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/user/upload_image/{id}")
    ResponseEntity<String> uploadImage(@PathVariable Long id, MultipartFile file) throws IOException {
        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
            throw new UnauthorizedException("Unauthorized update with user id : "+id);
        String url = userService.uploadImage(id,file);
        return  new ResponseEntity<>("url :" + url ,HttpStatus.OK);
    }
//    @PostMapping("/user/change_password/{id}")
//    ResponseEntity<String> changePassword(@PathVariable Long id) {
////        if(!permissionUpdate.hasPermissionUpdate(id, SecurityContextHolder.getContext().getAuthentication()))
////            throw new UnauthorizedException("Unauthorized update with user id : "+id);
////        ipm late
//        return  new ResponseEntity<>("success"  ,HttpStatus.OK);
//    }
}
