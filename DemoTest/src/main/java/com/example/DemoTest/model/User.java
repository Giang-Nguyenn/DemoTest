package com.example.DemoTest.model;

import com.example.DemoTest.core.model_abtract.BaseModel;
import com.example.DemoTest.core.penum.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class User extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,updatable = false)
//    @Pattern(regexp = "^[a-z0-9]{3,15}$")
    private String username;

    @Column(nullable = false)
    private String password;

    private  Long point = 0L;

    @Email
    private String email;
    private String fullName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.PERSIST) //xoa
    @JoinColumn(name = "role")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

    private String image;

    private String note;
    @Column(columnDefinition="bit default true")
    private boolean isActivity=true;



}
