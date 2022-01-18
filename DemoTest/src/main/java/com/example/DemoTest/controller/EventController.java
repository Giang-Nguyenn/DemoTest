package com.example.DemoTest.controller;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.EventSeachForGameDTO;
import com.example.DemoTest.dto.EventSeachForUserDTO;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.CustomUserDetails;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.repository.EventRepository;
import com.example.DemoTest.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {
    private KafkaTemplate<String, String> template;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    EventRepository eventRepository;

    public EventController(KafkaTemplate<String, String> template) {
        this.template = template;
//        this.myTopicConsumer = myTopicConsumer;
    }
    
    @Autowired
    EventService eventService;

    @GetMapping("/event/user/{user_id}")
    ResponseEntity<List<EventSeachForUserDTO>> getEventForUser(@PathVariable Long user_id,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                               Pageable pageable){
        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForUserListDTO(
                eventRepository.getEventForUser(user_id,start,end,pageable)),HttpStatus.OK );
    }
    @GetMapping("/event/game/{game_id}")
    ResponseEntity<List<EventSeachForGameDTO>> getEventForGame(@PathVariable Long game_id,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                               Pageable pageable){

        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForGameListDTO(
                eventRepository.getEventForGame(game_id,start,end,pageable)),HttpStatus.OK );
    }

    @PostMapping("/event")
    ResponseEntity addEvent(@RequestBody EventDTO eventDTO){
        //check resquest
        CustomUserDetails customUserDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = eventService.addEvent(customUserDetails.getId(),eventDTO);
//        long second = ChronoUnit.SECONDS.between(eventDTO.getStartAt(), eventDTO.getStartAt1());
//        System.out.println(second);
        template.send("test",customUserDetails.getId().toString()+","+event.getStatus()+","+event.getCreatedAt());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/event/count")
    ResponseEntity<String> getCountUserAndGameAboutTime(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                        Pageable pageable){
        return new ResponseEntity<>("suceess",HttpStatus.OK);
    }
}
