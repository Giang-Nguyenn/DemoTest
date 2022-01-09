package com.example.DemoTest.model;

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
@Table(name="User")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-z0-9]{3,15}$")
    private String userName;

    @Column(nullable = false)
    private String passWord;

    @Email
    private String email;

    private String fullName;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

    private String image;

    private String note;

    @Column(columnDefinition="bit default 1")
    private boolean isActivity;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private  LocalDateTime updateAt;


}
