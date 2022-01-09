package com.example.DemoTest.repository;

import com.example.DemoTest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    Page<User> findAll(Pageable pageable);
    boolean existsByUserName(String userName);


}
