package com.example.DemoTest.model;

import com.example.DemoTest.core.model_abtract.BaseModel;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Game extends BaseModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String image;

}
