package com.example.DemoTest.service;

import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.dto.UserGameDTO;
import com.example.DemoTest.exception.AlreadyExistsException;
import com.example.DemoTest.mapper.IUserGameMapper;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.UserGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGameService {
    @Autowired
    UserGameRepository userGameRepository;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;

    public UserGame save(UserGame userGame){
        return userGameRepository.save(userGame);
    }

    public List<UserGame> findAllUserGame(Pageable pageable){
        return userGameRepository.findAllUserGame(pageable);
    }

    public List<User> getUserOfGame(Long id, Pageable pageable){
        return userGameRepository.getUserOfGame(id,pageable);
    }

    public List<Game> getGameOfUser(Long id, Pageable pageable){
        return userGameRepository.getGameOfUser(id,pageable);
    }

    public UserGame addUserGame(UserGameDTO userGameDTO){
        UserGame userGame= IUserGameMapper.INSTANCE.toUserGame(userGameDTO);
        User user = userService.findUserById(userGameDTO.getUser().getId());
        Game game = gameService.findById(userGameDTO.getGame().getId());
        if(userGameRepository.existsByUserAndGame(user,game)) throw new AlreadyExistsException(String.format("AlreadyExists UserGame with user_id : %s,game_id: %s",
                userGameDTO.getUser().getId(), userGameDTO.getUser().getId()));
        userGame.setUser(user);
        userGame.setGame(game);
        return userGameRepository.save(userGame);
    }
}
