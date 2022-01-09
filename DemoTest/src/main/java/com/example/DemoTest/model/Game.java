package com.example.DemoTest.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    @Size(max = 100)
    private String name;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private  LocalDateTime updateAt;
}
