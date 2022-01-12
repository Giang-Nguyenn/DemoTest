package com.example.DemoTest.controller;

import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.dto.UserGameDTO;
import com.example.DemoTest.mapper.IUserGameMapper;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.User;
import com.example.DemoTest.repository.UserGameRepository;
import com.example.DemoTest.service.UserGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user_game")
public class UserGameController {
    @Autowired
    UserGameService userGameService;

    @Autowired
    UserGameRepository userGameRepository;
    @GetMapping("")
    ResponseEntity<List<UserGameDTO>> get(Pageable pageable){
        List<UserGameDTO> userGameDTOS= IUserGameMapper.INSTANCE.userGameToListDTO(userGameService.findAll(pageable));
        return new ResponseEntity<>(userGameDTOS, HttpStatus.OK);
    }
    @GetMapping("/user/{}")
    String getAllGame(@PathVariable Long id){
//        userGameService.getUserOfGame()
        return "List game user play";
    }
//    @GetMapping("/game/{id}")
//    ResponseEntity<List<UserDTOBasic>> getAllUser(@PathVariable Long id,Pageable pageable){
//        List<UserDTOBasic> userGameDTOS = IUserMapper.INSTANCE.userToListDTOBasic(userGameRepository.getUserOfGame(id,pageable));
//        return new ResponseEntity<>(userGameDTOS,HttpStatus.OK);
//    }

    @PostMapping("")
    String newUserGame(){
        return "user play game";
    }
}
