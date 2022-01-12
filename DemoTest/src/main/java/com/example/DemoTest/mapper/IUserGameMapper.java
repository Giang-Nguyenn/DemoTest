package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.dto.UserGameDTO;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(uses = {IUserMapper.class,IGameMapper.class})
public interface IUserGameMapper {
    IUserGameMapper INSTANCE= Mappers.getMapper(IUserGameMapper.class);
//    @Mapping(source = "user",target = "user_id",qualifiedByName = "userToUserId")
//    @Mapping(source = "game",target = "game_id",qualifiedByName = "gameToGameId")
    UserGameDTO userGameToDTO(UserGame userGame);
    List<UserGameDTO> userGameToListDTO(Page<UserGame> userGames);
//
//    @Named("userToUserId")
//    default Long userToUserId(User user){
//        return user.getId();
//    }
//
//    @Named("gameToGameId")
//    default Long gameToGameId(Game game){
//        return game.getId();
//    }
}
