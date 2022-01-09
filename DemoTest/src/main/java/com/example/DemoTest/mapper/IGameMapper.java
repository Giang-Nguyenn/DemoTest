package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface IGameMapper {
    IGameMapper INSTANCE= Mappers.getMapper(IGameMapper.class);
    GameDTO gameToDTO(Game game);
}
