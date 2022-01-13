package com.example.DemoTest.controller;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.mapper.IEventMapper;
import com.example.DemoTest.model.Event;
import com.example.DemoTest.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/event/{user_id}")
    String getEvent(@PathVariable Long user_id){
        System.out.println(new Date());
        System.out.println(user_id);
        System.out.println(LocalDateTime.now());
//        System.out.println(start);
//        System.out.println(end);
        return "123";
    }

    @PostMapping("/event")
    ResponseEntity addEvent(@RequestBody EventDTO eventDTO){
        eventService.addEvent(eventDTO).getId();
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
