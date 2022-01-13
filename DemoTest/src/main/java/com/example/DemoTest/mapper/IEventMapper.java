package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.GameDTO;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {IUserGameMapper.class})
public interface IEventMapper {
    IEventMapper INSTANCE= Mappers.getMapper(IEventMapper.class);
    EventDTO eventToDTO(Event event);
    List<EventDTO> EventToListDTO(List<Event> events);


    Event DTOToEvent(EventDTO eventDTO);
}
