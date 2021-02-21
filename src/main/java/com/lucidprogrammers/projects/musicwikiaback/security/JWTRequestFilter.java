package com.lucidprogrammers.projects.musicwikiaback.security;

import com.lucidprogrammers.projects.musicwikiaback.exception.TokenException;
import com.lucidprogrammers.projects.musicwikiaback.util.JWTTokenUtil;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final static String TOKEN_BEARER = "Bearer ";

    /**
     * Verifies if a token was sent and validates
     * it to retrieve and set the permissions for the
     * user in the spring security context, else,
     * resumes HTTP chain
     *
     * @param httpServletRequest HTTP request servlet object
     * @param httpServletResponse HTTP response servlet object
     * @param filterChain HTTP filter chain
     */
    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        final String requestTokenHeader = httpServletRequest.getHeader(HEADER_AUTHORIZATION);

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(TOKEN_BEARER)) {
            String token = requestTokenHeader.substring(7);
            String email;

            try {
                email = JWTTokenUtil.getClaimFromToken(token, Claims::getSubject);
            } catch (ClaimJwtException claimJwtException) {
                throw new TokenException(claimJwtException.getMessage());
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            email, null, JWTTokenUtil.getAuthoritiesFromToken(token)
                    );

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
