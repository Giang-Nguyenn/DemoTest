package com.example.DemoTest.repository;

import com.example.DemoTest.dto.EventDTO;
import com.example.DemoTest.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
}
