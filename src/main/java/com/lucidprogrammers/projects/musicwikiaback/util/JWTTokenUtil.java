package com.lucidprogrammers.projects.musicwikiaback.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility for JWT token creation and general
 * usage
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static final String TOKEN_ISSUER = "Music Wikia APP";

    private static String SECRET;

    /**
     * Sets the secret static field to the stored
     * jwt key
     *
     * @param secret JWT secret key stored in properties
     */
    @Value("${security.jwt.secret}")
    public void setSecretStatic(String secret) {
        SECRET = secret;
    }

    /**
     * Generates a token for the logging
     * user
     *
     * @param email user email
     * @param authorities user permissions
     * @return generated JWT token
     */
    public static String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        authorities.forEach(a -> roles.add(a.getAuthority()));
        map.put("authorities", String.join(";", roles));

        return Jwts.builder().setClaims(map)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .setIssuer(TOKEN_ISSUER)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * Retrieves the authorities from the
     * user JWT token
     *
     * @param token JWT generated token
     * @return list of authorities
     */
    public static List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        String authoritiesString = (String) getClaimFromToken(token, c -> c.get("authorities"));

        return Arrays.stream(authoritiesString.split(Constants.ROLE_SEPARATOR))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an specified claim from the token
     *
     * @param token JWT generated token
     * @param claimsResolver token claim lambda expression
     * @param <T> token claim
     * @return required token claim
     */
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtains all the claims form a
     * token
     *
     * @param token JWT generated token
     * @return all the claims of the token
     */
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

}
