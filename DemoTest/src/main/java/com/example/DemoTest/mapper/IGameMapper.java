package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper()
public interface IGameMapper {
    IGameMapper INSTANCE= Mappers.getMapper(IGameMapper.class);
    GameDTO gameToDTO(Game game);
    List<GameDTO> gameToListDTO(List<Game> game);
}
