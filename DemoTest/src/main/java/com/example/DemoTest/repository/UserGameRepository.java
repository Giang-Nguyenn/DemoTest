package com.example.DemoTest.repository;

import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame,Long> {
//    @Query(
//            value = "SELECT com.example.DemoTest.dto.UserDTOBasic(u.id)"+
//                    " FROM user u JOIN user_game ON user_game.user_id=?1",
//            nativeQuery = true)
//    List<User> getUserOfGame(Long id, Pageable pageable);

    @Query(
            value = "SELECT * FROM UserGame ug WHERE ug.user_id = ?1",
            nativeQuery = true)
    Page<UserGame> getGameOfUser(Long id, Pageable pageable);

}
