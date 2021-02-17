package com.lucidprogrammers.projects.musicwikiaback.security;

import com.lucidprogrammers.projects.musicwikiaback.util.JWTTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * HTTP security filter for handling
 * the access to the resources of the
 * application
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    /**
     * Verifies if a token was sent and validates
     * it to retrieve and set the permissions for the
     * user in the spring security context, else,
     * resumes HTTP chain
     *
     * @param httpServletRequest HTTP request servlet object
     * @param httpServletResponse HTTP response servlet object
     * @param filterChain HTTP filter chain
     * @throws ServletException on servlet error
     * @throws IOException on error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            String username;

            try {
                username = JWTTokenUtil.getClaimFromToken(token, Claims::getSubject);
            } catch (ExpiredJwtException expiredJwtException) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
            } catch (Exception exception){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is invalid");
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username, null, JWTTokenUtil.getAuthoritiesFromToken(token)
                    );

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
