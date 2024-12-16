package com.Zi.spring_IMS.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    // main users table in database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment, not using deleted IDs
    @Column
    private Integer id;

    @Column(nullable = false)
    private String name; //Unique

    @Column(nullable = false, updatable = false) //need to add more validation
    private String username; //Unique

    @Column(nullable = false)// need to add more validation
    private String password; //Hashed

    @Enumerated(EnumType.STRING)
    private Role role = Role.STAFF; //e.g., ADMIN, MANAGER, STAFF

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at; // sign_up time

    @Column(nullable = false)
    private LocalDateTime update_at; // last update profile time?

    @Enumerated(EnumType.STRING)
    private Account_Status status = Account_Status.ACTIVE;
}
