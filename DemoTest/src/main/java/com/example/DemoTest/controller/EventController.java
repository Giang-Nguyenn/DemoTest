package com.example.DemoTest.controller;

import com.example.DemoTest.core.kafka_redis.kafka.EventMessageKafka;
import com.example.DemoTest.dto.EventCreateDTO;
import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.EventSeachForGameDTO;
import com.example.DemoTest.dto.EventSeachForUserDTO;
import com.example.DemoTest.dto.statistic.*;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.CustomUserDetails;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.repository.EventRepository;
import com.example.DemoTest.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {
//    private final KafkaTemplate<String, Object> template;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventService eventService;

    @GetMapping("/user/{id}")
    ResponseEntity<List<EventSeachForUserDTO>> getListEventForUser( @PathVariable(name="id") Long userId,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForUserListDTO(
                eventService.getListEventForUser(userId,start,end,pageable)),HttpStatus.OK );
    }

    @GetMapping("/game/{id}")
    ResponseEntity<List<EventSeachForGameDTO>> getListEventForGame( @PathVariable(name="id") Long gameId,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){

        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForGameListDTO(
                eventService.getListEventForGame(gameId,start,end,pageable)),HttpStatus.OK );
    }

    @PostMapping("")
    ResponseEntity addEvent(@RequestBody EventCreateDTO eventCreateDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        //check resquest: ipm late
//        CustomUserDetails customUserDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = eventService.addEvent(customUserDetails.getId(),eventCreateDTO);
//        EventMessageKafka kafkaMessageObject=new EventMessageKafka(customUserDetails.getId(),event.getStatus(),event.getCreatedAt());
//        template.send("test",kafkaMessageObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //Số lượng user và game trong 1 khoảng thời gian
    @GetMapping("/count")
    ResponseEntity<List<CountUserAndGameAboutTimeDTO>> getCountUserAndGameAboutTime(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                                    Pageable pageable){
        return new ResponseEntity<>(eventService.getCountUserAndGameAboutTime(start,end),HttpStatus.OK);
    }

    //Số lượng event của các game trong 1 khaongr thời gian
    @GetMapping("/game_about_time")
    ResponseEntity<List<EventForManyGameDTO>> countEventForManyGame(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(eventService.countEventForManyGameAboutTime(start,end),HttpStatus.OK);
    }

    //Số lượng event của các user trong 1 khaongr thời gian
    @GetMapping("/user_about_time")
    ResponseEntity<List<EventForManyUserDTO>> countEventForManyUser(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(eventService.countEventForManyUserAboutTime(start,end),HttpStatus.OK);
    }


    // thống kê số lượng event của game theo ngày
    @GetMapping("/statistic/game/{id}")
    ResponseEntity<List<IGameEventByDate>> countEventGameByDate(@PathVariable(name="id") Long gameId,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                Pageable pageable){
        System.out.println(userDetails);
        return new ResponseEntity<>(eventService.countEventGameByDate(gameId,start,end),HttpStatus.OK);
    }

    // thống kê số lượng event của user theo ngày
    @GetMapping("/statistic/user/{id}")
    ResponseEntity<List<IUserEventByDate>> countEventUserByDate(@PathVariable(name="id") Long userId,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                Pageable pageable){
        return new ResponseEntity<>(eventService.countEventUserByDate(userId,start,end),HttpStatus.OK);
    }
}
