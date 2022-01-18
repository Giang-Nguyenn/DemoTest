package com.example.DemoTest.repository;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("SELECT e FROM Event e WHERE  "+
            "e.startAt BETWEEN ?2 AND ?3 "+
    "AND e.userGame.id IN (SELECT ug.id FROM UserGame ug,User u WHERE ug.user.id=u.id AND u.id=?1)")
    List<Event> getEventForUser(Long user_id, LocalDateTime start, LocalDateTime end, Pageable pageable);


    @Query("SELECT e FROM Event e WHERE "+
            "e.startAt BETWEEN ?2 AND ?3 "+
            "AND e.userGame.id IN (SELECT ug.id FROM UserGame ug,Game g WHERE ug.user.id=g.id AND g.id=?1)")
    List<Event> getEventForGame(Long game_id,LocalDateTime start,LocalDateTime end,Pageable pageable);



//    @Query(value = "SELECT COUNT(DISTINCT user_game.user_id),COUNT(DISTINCT user_game.game_id) " +
//            "FROM event,user_game " +
//            "WHERE event.user_game_id=user_game.id " +
//            "AND start_at BEETWEEN ?1 AND ?2 ")
//    List<Event> getCountUserAndGameAboutTime(LocalDateTime start,LocalDateTime end,Pageable pageable);

 }
