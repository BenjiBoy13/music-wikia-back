package com.lucidprogrammers.projects.musicwikiaback.database.entity;

import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity that holds the user information
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String fullName;

    @Column(length = 60, nullable = false)
    private String username;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(nullable = false)
    private Character status = Constants.USER_STATUS_ACTIVE;

    @Column(length = 50, nullable = false)
    private String role = Constants.ROL_USER;

    private Date createdDate = new Date();

}
