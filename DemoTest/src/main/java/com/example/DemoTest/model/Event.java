package com.example.DemoTest.model;

import com.example.DemoTest.core.model_abtract.BaseModel;
import com.example.DemoTest.core.penum.StatusEvent;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Event extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "user_game_id")
    private UserGame userGame;
    @Enumerated(EnumType.STRING)
    private StatusEvent status;

//    private LocalDateTime startAt;

}
