package com.example.DemoTest.repository;

import com.example.DemoTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    List<User> findAll();
    boolean existsByUserName(String userName);
}
