package com.example.DemoTest.service;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.exception.ForbiddenException;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserGameService userGameService;

    public Event addEvent(Long user_id ,EventDTO eventDTO){
       Optional<UserGame> optional = userGameService.findById(eventDTO.getUserGame().getId());
        if(!optional.isPresent()) throw new NotFoundException("NotFound UserGame with id :  "+ eventDTO.getUserGame().getId());
        if(user_id!=optional.get().getGame().getId()) throw new ForbiddenException("User game not include user :  ");
        Event event= IEventMapper.INSTANCE.DTOToEvent(eventDTO);
        event.setUserGame(optional.get());
        return eventRepository.save(event);
    }

}
