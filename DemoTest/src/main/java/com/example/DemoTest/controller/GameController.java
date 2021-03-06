package com.example.DemoTest.controller;

import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.mapper.IGameMapper;
import com.example.DemoTest.mapper.IUserMapper;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GameController {
//    @Autowired
    private final GameService gameService;

    @GetMapping("/game")
    ResponseEntity<List<GameDTO>> listGame(Pageable pageable){
        List<Game> lgame= gameService.findAllGame(pageable);
        List<GameDTO> gameDTOS=IGameMapper.INSTANCE.gameToListDTO(lgame);
//        for(Game game:lgame){
//            gameDTOS.add(IGameMapper.INSTANCE.gameToDTO(game));
//        }
        return new ResponseEntity<>(gameDTOS, HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    ResponseEntity<GameDTO> retrieveGame(@PathVariable Long id){
        return new ResponseEntity<>(IGameMapper.INSTANCE.gameToDTO(gameService.findById(id)), HttpStatus.OK);
    }

    @PatchMapping("/game/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<GameDTO> partialUpdate(@PathVariable Long id, @RequestBody  HashMap<Object,Object> fields){
        fields.remove("id");
        Game game = gameService.findById(id);
        fields.forEach((k,v)->{
            Field field= ReflectionUtils.findField(Game.class,(String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field,game,v);
        });
        GameDTO gameDTO= IGameMapper.INSTANCE.gameToDTO(gameService.save(game));
        return new ResponseEntity<>(gameDTO,HttpStatus.PARTIAL_CONTENT);
    }

    @DeleteMapping("/game/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<String> deleteGame(@PathVariable Long id){
        Boolean status= gameService.deleteById(id);
        return  new ResponseEntity<>("",HttpStatus.OK);
    }

    @PostMapping("game")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<GameDTO> addGame(@RequestBody GameDTO gameDTO){
        return  new ResponseEntity<>(IGameMapper.INSTANCE.gameToDTO(gameService.add(gameDTO)), HttpStatus.CREATED);
    }
}
