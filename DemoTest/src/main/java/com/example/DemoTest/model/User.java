package com.example.DemoTest.model;

import com.example.DemoTest.core.penum.Gender;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="User")
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^ [A-Za-z] \\ w {5,29} $")
    private String userName;

    @Column(nullable = false)
    private String passWord;

    @Email
    private String email;

    private String fullName;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String image;

    private String note;
    private boolean isActivity;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createAt;
}
