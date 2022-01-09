package com.example.DemoTest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
public class Permissions {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String action;

    @ManyToMany(mappedBy = "permissions")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Role> role = new LinkedHashSet<>();

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private  LocalDateTime updateAt;

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
