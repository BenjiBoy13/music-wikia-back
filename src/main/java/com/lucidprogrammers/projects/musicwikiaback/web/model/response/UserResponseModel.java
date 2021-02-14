package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseModel {

    private String fullName;

    private String username;

    private String email;

    private String role;

    private Date createdDate;

    private String token;

}
