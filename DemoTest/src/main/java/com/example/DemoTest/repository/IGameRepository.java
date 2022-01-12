package com.example.DemoTest.repository;

import com.example.DemoTest.model.Game;
import com.example.DemoTest.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IGameRepository extends JpaRepository<Game,Long> {
    @Query("SELECT g FROM Game g")
    List<Game> findAllGame(Pageable pageable);
}
