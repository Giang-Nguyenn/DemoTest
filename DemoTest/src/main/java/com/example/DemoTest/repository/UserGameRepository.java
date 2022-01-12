package com.example.DemoTest.repository;

import com.example.DemoTest.dto.iUserDTO;
import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import com.example.DemoTest.model.UserGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.Session;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame,Long> {
    @Query("SELECT u FROM UserGame u")
    List<UserGame> findAllUserGame(Pageable pageable);

//    @Query(
//            value = "SELECT *"+
//                    " FROM user u WHERE u.id=?1",
//            nativeQuery = true)
    @Query("SELECT u FROM User u ,UserGame ug WHERE u.id = ug.user AND ug.game.id=?1")
    List<User> getUserOfGame(Long id,Pageable pageable);

//    @Query(
//            value = "SELECT new com.example.DemoTest.dto.UserDTOBasic(u.id as id ,u.user_name as userName,user.full_name as fullName,u.phone as phone,u.image as image)"+
//                    " FROM user u WHERE u.id=?1",
//            nativeQuery = true)
//      List<iUserDTO> getUserOfGame(Long id);
    @Query("SELECT g FROM Game g,UserGame ug WHERE g.id=ug.game AND ug.user.id=?1")
    List<Game> getGameOfUser(Long id, Pageable pageable);

    boolean existsByUserAndGame(User user,Game game);



}
