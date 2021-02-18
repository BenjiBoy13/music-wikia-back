package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * HTTP response that holds the token data
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class TokenResponseModel implements ResponseModel {

    private String issuer;

    private Date expirationDate;

    private String token;

}
