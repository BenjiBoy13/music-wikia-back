package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * HTTP response that holds the user data
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponseModel implements ResponseModel {

    private String fullName;

    private String username;

    private String email;

    private String role;

    private Date createdDate;

}
