package com.example.DemoTest.repository;

import com.example.DemoTest.dto.statistic.*;
import com.example.DemoTest.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
//    JPA
//    List event theo user
    @Query("SELECT e FROM Event e WHERE  "
            + "e.createdAt BETWEEN :start AND :end "
            + "AND e.userGame.id IN (SELECT ug.id FROM UserGame ug,User u WHERE ug.user.id=u.id AND u.id=:userId)")
    List<Event> getListEventForUser(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);

//    List event theo game
    @Query("SELECT e FROM Event e WHERE "
            + "e.createdAt BETWEEN :start AND :end "
            + "AND e.userGame.id IN (SELECT ug.id FROM UserGame ug,Game g WHERE ug.user.id=g.id AND g.id=:gameId)")
    List<Event> getListEventForGame(Long gameId,LocalDateTime start,LocalDateTime end,Pageable pageable);



//    @Query(value = "SELECT COUNT(DISTINCT user_game.user_id) as user,COUNT(DISTINCT user_game.game_id) as game " +
//            "FROM event,user_game " +
//            "WHERE event.user_game_id=user_game.id " +
//            "AND event.start_at BETWEEN :start AND :end ",nativeQuery = true)
//    List<ICountUserAndGameAboutTimeDTO> getCountUserAndGameAboutTime1(LocalDateTime start, LocalDateTime end);

//    Số lượng user và game trong một khoảng thời gian
    @Query(value = "SELECT new com.example.DemoTest.dto.statistic.CountUserAndGameAboutTimeDTO(COUNT(DISTINCT ug.user.id) as user,COUNT(DISTINCT ug.game.id) as game) "
            + "FROM Event e,UserGame ug "
            + "WHERE e.userGame=ug "
            + "AND e.createdAt BETWEEN :start AND :end ")
    List<CountUserAndGameAboutTimeDTO> getCountUserAndGameAboutTime(LocalDateTime start, LocalDateTime end);



//    Số lượng event của các game trong khoảng thời gian
    @Query(value = "SELECT new com.example.DemoTest.dto.statistic.EventForManyGameDTO(g.id as gameId,count(g.id) as sumEvent) "
            + "FROM Event e LEFT JOIN UserGame ug ON e.userGame=ug "
            + "LEFT JOIN Game g ON ug.game=g "
            + "WHERE e.createdAt BETWEEN :start AND :end "
            + "GROUP BY g.id ")
    List<EventForManyGameDTO> countEventForManyGameAboutTime(LocalDateTime start, LocalDateTime end);

//    Số lượng event của các user trong 1 khoảng thời gian
    @Query(value = "SELECT new com.example.DemoTest.dto.statistic.EventForManyUserDTO(u.id as userId,count(u.id) as sumEvent) "
            + "FROM Event e LEFT JOIN UserGame ug ON e.userGame=ug "
            + "LEFT JOIN User u ON ug.user=u "
            + "WHERE e.createdAt BETWEEN :start AND :end "
            + "GROUP BY u.id ")
    List<EventForManyUserDTO> countEventForManyUserAboutTime(LocalDateTime start, LocalDateTime end);


//    native
//    Theo game thống kê số event theo ngày
    @Query(value = "SELECT date(event.created_at) as date, count(game.id) as sumEvent "
            + "FROM event LEFT JOIN user_game  ON event.user_game_id=user_game.id "
            + "LEFT JOIN game ON user_game.game_id=game.id "
            + "WHERE game.id=:gameId "
            + "AND date(event.created_at) >= date(:start) AND date(event.created_at) <= date(:end) "
            + "GROUP BY date ",nativeQuery = true)
    List<IGameEventByDate> countEventGameByDate(Long gameId,LocalDateTime start, LocalDateTime end);

//    Theo user thống kê số event theo ngày
    @Query(value = "SELECT date(event.created_at) as date, count(user.id) as sumEvent "
            + "FROM event LEFT JOIN user_game  ON event.user_game_id=user_game.id "
            + "LEFT JOIN user ON user_game.user_id=user.id "
            + "WHERE user.id=:userId "
            + "AND date(event.created_at) >= date(:start) AND date(event.created_at) <= date(:end) "
            + "GROUP BY date ",nativeQuery = true)
    List<IUserEventByDate> countEventUserByDate(Long userId, LocalDateTime start, LocalDateTime end);
 }
