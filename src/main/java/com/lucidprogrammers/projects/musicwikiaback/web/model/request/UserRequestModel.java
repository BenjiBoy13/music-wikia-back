package com.lucidprogrammers.projects.musicwikiaback.web.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the HTTP request model expected
 * data for user.
 *
 * @author Benjamin Gil FLores
 * @since 1.0.0
 */
@Getter
@Setter
public class UserRequestModel {

    private String fullName;

    private String username;

    private String email;

    private String password;

}
