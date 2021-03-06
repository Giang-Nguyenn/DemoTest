package com.example.DemoTest.repository;

import com.example.DemoTest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u")
    List<User> findAllUser(Pageable pageable);

    boolean existsByUsername(String username);



}
