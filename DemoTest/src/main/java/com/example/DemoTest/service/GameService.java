package com.example.DemoTest.service;

import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.repository.IGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class GameService {
//    @Autowired
    private final IGameRepository gameRepository;

    public Game findById(Long id){
        return gameRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("NotFound game id : %s ",id))
        );
    }
    public List<Game> findAllGame(Pageable pageable){
        return (List<Game>) gameRepository.findAllGame(pageable);
    }

    public Boolean deleteById(Long id){
        if (!gameRepository.findById(id).isPresent()) throw new NotFoundException(String.format("NotFound game id : %s ",id));
        gameRepository.deleteById(id);
        return true;
    }

    public Game save(Game game){
        return gameRepository.save(game);
    }

    public Game add(GameDTO gameDTO){
        Game game=new Game();
        game.setName(gameDTO.getName());
        return gameRepository.save(game);
    }
}
