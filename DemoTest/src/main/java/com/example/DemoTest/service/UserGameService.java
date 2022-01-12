package com.example.DemoTest.service;

import com.example.DemoTest.dto.UserDTOBasic;
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

    public UserGame save(UserGame userGame){
        return userGameRepository.save(userGame);
    }

    public Page<UserGame> findAll(Pageable pageable){
        return userGameRepository.findAll(pageable);
    }

    public List<User> getUserOfGame(Long id, Pageable pageable){
        System.out.println(userGameRepository.getUserOfGame(id,pageable));
        return userGameRepository.getUserOfGame(id,pageable);
    }
}
