package com.lucidprogrammers.projects.musicwikiaback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public static final String TOKEN_ISSUER = "Music Wikia APP";

    @Value("${security.jwt.secret}")
    private String secret;
    
    public String generateToken(UserDetails userDetails) {
        List<String> roles = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
        map.put("authorities", String.join(";", roles));

        return Jwts.builder().setClaims(map)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .setIssuer(TOKEN_ISSUER)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Object authoritiesPlane = getClaimFromToken(token, c -> c.get("authorities"));
        System.out.println(authoritiesPlane.toString());
        String authoritiesString = (String) getClaimFromToken(token, c -> c.get("authorities"));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Arrays.asList(authoritiesString.split(";"))
                .forEach(s -> grantedAuthorities.add(new SimpleGrantedAuthority(s)));
        return grantedAuthorities;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, String email) {
        final String tokenSubject = getUsernameFromToken(token);
        return (tokenSubject.equals(email) && !isTokenExpired(token));
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
