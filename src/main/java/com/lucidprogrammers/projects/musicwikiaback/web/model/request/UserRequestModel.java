package com.lucidprogrammers.projects.musicwikiaback.web.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestModel {

    private String fullName;

    private String username;

    private String email;

    private String password;

}
