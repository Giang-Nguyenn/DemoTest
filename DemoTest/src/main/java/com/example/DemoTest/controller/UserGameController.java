package com.example.DemoTest.controller;

import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.dto.UserGameDTO;
import com.example.DemoTest.mapper.IGameMapper;
import com.example.DemoTest.mapper.IUserGameMapper;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.IUserRepository;
import com.example.DemoTest.repository.UserGameRepository;
import com.example.DemoTest.service.UserGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        List<UserGameDTO> userGameDTOS= IUserGameMapper.INSTANCE.userGameToListDTO(userGameService.findAllUserGame(pageable));
        return new ResponseEntity<>(userGameDTOS, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<List<GameDTO>> getAllGame(@PathVariable Long id,Pageable pageable){
        List<GameDTO> gameDTOList= IGameMapper.INSTANCE.gameToListDTO((userGameService.getGameOfUser(id,pageable)));
        return new ResponseEntity<>(gameDTOList,HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    ResponseEntity<List<UserDTOBasic>> getAllUser(@PathVariable Long id,Pageable pageable){
        List<UserDTOBasic> userDTOBasicList=IUserMapper.INSTANCE.userToListDTOBasic(userGameService.getUserOfGame(id,pageable));
        return new ResponseEntity<>(userDTOBasicList,HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<UserGameDTO> newUserGame(@RequestBody UserGameDTO userGameDTO){
        userGameDTO.setId(null);
        return new ResponseEntity<>(IUserGameMapper.INSTANCE.userGameToDTO(userGameService.addUserGame(userGameDTO)), HttpStatus.CREATED);
    }
}
