package com.lucidprogrammers.projects.musicwikiaback.exception;

import javax.naming.AuthenticationException;

/**
 * Authentication exception related to the JWT
 * token, thrown when the token is invalid,
 * expired or malformed
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
public class TokenException extends AuthenticationException {

    public TokenException(String explanation) {
        super(explanation);
    }

}
