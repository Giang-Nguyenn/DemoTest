package com.example.DemoTest.repository;

import com.example.DemoTest.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameRepository extends JpaRepository<Game,Long> {
}
