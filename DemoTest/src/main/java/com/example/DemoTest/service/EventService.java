package com.example.DemoTest.service;

import com.example.DemoTest.core.kafka_redis.kafka.EventMessageKafka;
import com.example.DemoTest.dto.EventCreateDTO;
import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.statistic.*;
import com.example.DemoTest.exception.ForbiddenException;
import com.example.DemoTest.exception.NotFoundException;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.CustomUserDetails;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.model.UserGame;
import com.example.DemoTest.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class EventService {
//    @Autowired
    private final EventRepository eventRepository;

//    @Autowired
    private final UserGameService userGameService;
    private final KafkaTemplate<String, Object> template;

    public Event addEvent(Long user_id , EventCreateDTO eventCreateDTO){
       Optional<UserGame> optional = userGameService.findById(eventCreateDTO.getUserGameId());
        if(!optional.isPresent()) throw new NotFoundException("NotFound UserGame with id :  "+ eventCreateDTO.getUserGameId());
        if(user_id!=optional.get().getUser().getId()) throw new ForbiddenException("User game not include user :  "+user_id);
//        Event event= IEventMapper.INSTANCE.DTOToEvent(eventDTO);
        Event event= new Event();
        event.setUserGame(optional.get());
        event.setStatus(eventCreateDTO.getStatus());
        Event event1 = eventRepository.save(event);
        EventMessageKafka kafkaMessageObject=new EventMessageKafka(user_id,event1.getStatus(),event1.getCreatedAt());
        template.send("test",kafkaMessageObject);
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
