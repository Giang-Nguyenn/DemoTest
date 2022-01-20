package com.example.DemoTest.service;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.statistic.*;
import com.example.DemoTest.exception.ForbiddenException;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        if(user_id!=optional.get().getUser().getId()) throw new ForbiddenException("User game not include user :  "+user_id);
        Event event= IEventMapper.INSTANCE.DTOToEvent(eventDTO);
        event.setUserGame(optional.get());
        return eventRepository.save(event);
    }

    public List<Event> getListEventForUser(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable){
        return  eventRepository.getListEventForUser(userId,start,end,pageable);
    };

    public List<Event> getListEventForGame(Long gameId, LocalDateTime start, LocalDateTime end, Pageable pageable){
        return  eventRepository.getListEventForUser(gameId,start,end,pageable);
    };

    public List<CountUserAndGameAboutTimeDTO> getCountUserAndGameAboutTime(LocalDateTime start, LocalDateTime end){
        return  eventRepository.getCountUserAndGameAboutTime(start,end);
    };

    public List<EventForManyGameDTO> countEventForManyGameAboutTime(LocalDateTime start, LocalDateTime end){
        return eventRepository.countEventForManyGameAboutTime(start,end);
    };

    public List<EventForManyUserDTO> countEventForManyUserAboutTime(LocalDateTime start, LocalDateTime end){
        return eventRepository.countEventForManyUserAboutTime(start,end);
    };

    public List<IGameEventByDate> countEventGameByDate(Long gameId, LocalDateTime start, LocalDateTime end){
        return eventRepository.countEventGameByDate(gameId,start,end);
    };

    public List<IUserEventByDate> countEventUserByDate(Long userId, LocalDateTime start, LocalDateTime end){
        return  eventRepository.countEventUserByDate(userId,start,end);
    };

}
