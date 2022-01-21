package com.example.DemoTest.service;

import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.dto.UserGameCreateDTO;
import com.example.DemoTest.dto.UserGameDTO;
import com.example.DemoTest.exception.AlreadyExistsException;
import com.example.DemoTest.mapper.IUserGameMapper;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.UserGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserGameService {
//    @Autowired
    private final UserGameRepository userGameRepository;
//    @Autowired
    private final UserService userService;
//    @Autowired
    private final GameService gameService;

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

    public UserGame addUserGame(UserGameCreateDTO userGameCreateDTO){
        UserGame userGame= new UserGame();
        User user = userService.findUserById(userGameCreateDTO.getUserId());
        Game game = gameService.findById(userGameCreateDTO.getGameId());
        if(userGameRepository.existsByUserAndGame(user,game)) throw new AlreadyExistsException(String.format("AlreadyExists UserGame with user_id : %s,game_id: %s",
                userGameCreateDTO.getUserId(), userGameCreateDTO.getGameId()));
        userGame.setUser(user);
        userGame.setGame(game);
        return userGameRepository.save(userGame);
    }

    Optional<UserGame> findById(Long id){
        return userGameRepository.findById(id);
    }

}
