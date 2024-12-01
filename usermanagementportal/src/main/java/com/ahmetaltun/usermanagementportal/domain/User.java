package com.ahmetaltun.usermanagementportal.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @since 01/12/2024
 * @email ahmet.altun60@gmail.com
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String userId;
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)

    private String userName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Column(nullable = true)
    private String profileImageUrl;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLoginDateDisplay;
    private LocalDate joinedDate;
    private String[] roles; // ROLE_USER, ROLE_ADMIN
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;


}
