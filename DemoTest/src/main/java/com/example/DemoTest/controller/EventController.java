package com.example.DemoTest.controller;

import com.example.DemoTest.core.kafka_redis.kafka.EventMessageKafka;
import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.dto.EventSeachForGameDTO;
import com.example.DemoTest.dto.EventSeachForUserDTO;
import com.example.DemoTest.dto.statistic.EventForManyGameDTO;
import com.example.DemoTest.dto.statistic.EventForManyUserDTO;
import com.example.DemoTest.dto.statistic.IGameEventByDate;
import com.example.DemoTest.dto.statistic.IUserEventByDate;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {
    private KafkaTemplate<String, Object> template;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventService eventService;

    public EventController(KafkaTemplate<String, Object> template) {
        this.template = template;
    }


    @GetMapping("/event/user/{id}")
    ResponseEntity<List<EventSeachForUserDTO>> getListEventForUser( @PathVariable(name="id") Long userId,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForUserListDTO(
                eventService.getListEventForUser(userId,start,end,pageable)),HttpStatus.OK );
    }

    @GetMapping("/event/game/{id}")
    ResponseEntity<List<EventSeachForGameDTO>> getListEventForGame( @PathVariable(name="id") Long gameId,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){

        return new ResponseEntity<>(IEventMapper.INSTANCE.eventToEventSeachForGameListDTO(
                eventService.getListEventForGame(gameId,start,end,pageable)),HttpStatus.OK );
    }

    @PostMapping("/event")
    ResponseEntity addEvent(@RequestBody EventDTO eventDTO){
        //check resquest: ipm late
        CustomUserDetails customUserDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = eventService.addEvent(customUserDetails.getId(),eventDTO);
        EventMessageKafka kafkaMessageObject=new EventMessageKafka(customUserDetails.getId(),event.getStatus(),event.getCreatedAt());
        template.send("test",kafkaMessageObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //Số lượng user và game trong 1 khoảng thời gian
    @GetMapping("/event/count")
    ResponseEntity<List<?>> getCountUserAndGameAboutTime(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                         Pageable pageable){
        return new ResponseEntity<>(eventService.getCountUserAndGameAboutTime(start,end),HttpStatus.OK);
    }




    //Số lượng event của các game trong 1 khaongr thời gian
    @GetMapping("/event/game_about_time")
    ResponseEntity<List<EventForManyGameDTO>> countEventForManyGame(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(eventService.countEventForManyGameAboutTime(start,end),HttpStatus.OK);
    }

    //Số lượng event của các user trong 1 khaongr thời gian
    @GetMapping("/event/user_about_time")
    ResponseEntity<List<EventForManyUserDTO>> countEventForManyUser(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                    Pageable pageable){
        return new ResponseEntity<>(eventService.countEventForManyUserAboutTime(start,end),HttpStatus.OK);
    }


    // thống kê số lượng event của game theo ngày
    @GetMapping("/event/statistic/game/{id}")
    ResponseEntity<List<IGameEventByDate>> countEventGameByDate(@PathVariable(name="id") Long gameId,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                Pageable pageable){
        return new ResponseEntity<>(eventService.countEventGameByDate(gameId,start,end),HttpStatus.OK);
    }
    // thống kê số lượng event của user theo ngày
    @GetMapping("/event/statistic/user/{id}")
    ResponseEntity<List<IUserEventByDate>> countEventUserByDate(@PathVariable(name="id") Long userId,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                Pageable pageable){
        return new ResponseEntity<>(eventService.countEventUserByDate(userId,start,end),HttpStatus.OK);
    }
}
