package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.EventSeachForGameDTO;
import com.example.DemoTest.dto.EventSeachForUserDTO;
import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.UserGame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {IUserGameMapper.class})
public interface IEventMapper {
    IEventMapper INSTANCE= Mappers.getMapper(IEventMapper.class);

    EventDTO eventToDTO(Event event);
    List<EventDTO> EventToListDTO(List<Event> events);


    @Mapping(source = "userGame",target = "game_id",qualifiedByName = "getGameId")
    EventSeachForUserDTO eventToEventSeachForUserDTO(Event event);
    List<EventSeachForUserDTO> eventToEventSeachForUserListDTO(List<Event> events);

    @Mapping(source = "userGame",target = "user_id",qualifiedByName = "getUserId")
    EventSeachForGameDTO eventToEventSeachForGameDTO(Event event);
    List<EventSeachForGameDTO> eventToEventSeachForGameListDTO(List<Event> events);


    Event DTOToEvent(EventDTO eventDTO);



    @Named("getGameId")
    default Long getGameId(UserGame userGame) {
        return userGame.getGame().getId();
    }
    @Named("getUserId")
    default Long getUserId(UserGame userGame) {
        return userGame.getUser().getId();
    }

}
