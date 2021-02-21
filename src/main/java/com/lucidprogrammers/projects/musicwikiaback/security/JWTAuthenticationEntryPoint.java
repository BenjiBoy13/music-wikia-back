package com.lucidprogrammers.projects.musicwikiaback.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.HTTPErrorResponse;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.MusicWikiaResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Authentication exception interceptor
 *
 * @author Benjamin Gil FLores
 * @since 1.0.0
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String ACCESS_OR_TOKEN_IS_INVALID = "You dont have access or token is invalid";

    /**
     * Handles authentication related exceptions and build
     * a generic HTTP error response
     *
     * @param httpServletRequest HTTP servlet request
     * @param httpServletResponse HTTP servlet response
     * @param e authentication exception
     * @throws IOException when object mapper cant build the json string
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        String json = new ObjectMapper().writeValueAsString(new MusicWikiaResponse<>(
                Constants.STATUS_MESSAGE_FAILURE,
                Constants.STATUS_TYPE_FAILURE,
                Constants.STATUS_CODE_FORBIDDEN,
                new HTTPErrorResponse(ACCESS_OR_TOKEN_IS_INVALID)
        ));

        httpServletResponse.setStatus(Constants.STATUS_CODE_FORBIDDEN);
        httpServletResponse.getWriter().write(json);
    }

}
