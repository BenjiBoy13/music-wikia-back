package com.lucidprogrammers.projects.musicwikiaback.app.service;

import com.lucidprogrammers.projects.musicwikiaback.database.entity.User;
import com.lucidprogrammers.projects.musicwikiaback.database.facade.UserFacade;
import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import com.lucidprogrammers.projects.musicwikiaback.util.JWTTokenUtil;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.TokenResponseModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.UserResponseModel;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles all business operations related
 * to the application user
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserFacade userFacade;

    private final AuthenticationManager authenticationManager;

    /**
     * Finds the logged in user in database and
     * creates spring user details object for
     * the spring security context
     *
     * @param s user email
     * @return constructed spring user details object
     */
    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userFacade.findByEmail(s);

        if (Objects.isNull(user))
            throw new UsernameNotFoundException("User was not found");

        List<GrantedAuthority> authorities = Arrays.stream(user.getRole().split(Constants.ROLE_SEPARATOR))
                .map(r -> new SimpleGrantedAuthority(Constants.ROLE_STARTER + r))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Constants.USER_STATUS_ACTIVE.equals(user.getStatus()),
                Constants.TRUE,
                Constants.TRUE,
                Constants.TRUE,
                authorities
        );
    }

    /**
     * Authenticates the user and returns the
     * generated token
     *
     * @param userRequestModel HTTP request containing user data
     * @return HTTP response containing user token
     */
    public TokenResponseModel login(UserRequestModel userRequestModel) {
        Authentication recentlyAuthenticatedUser =
                authenticate(userRequestModel.getEmail(), userRequestModel.getPassword());

        final String token = JWTTokenUtil.generateToken(
                ((UserDetails) recentlyAuthenticatedUser.getPrincipal()).getUsername(),
                recentlyAuthenticatedUser.getAuthorities()
        );

        return new TokenResponseModel(
                JWTTokenUtil.getClaimFromToken(token, Claims::getIssuer),
                JWTTokenUtil.getClaimFromToken(token, Claims::getExpiration),
                token
        );
    }

    /**
     * Creates a new user
     *
     * @param userRequestModel HTTP request containing user data
     * @return HTTP response containing new created user data
     */
    public UserResponseModel createUser(UserRequestModel userRequestModel) {
        User user = userFacade.createUser(userRequestModel);
        return userEntityToUserResponse(user);
    }

    /**
     * Transforms the user entity object to a user response
     * model
     *
     * @param user user entity
     * @return HTTP response model holding user data
     */
    private UserResponseModel userEntityToUserResponse(User user) {
        return new UserResponseModel(
                user.getFullName(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedDate()
        );
    }

    /**
     * Authenticates the user with spring
     * security context
     *
     * @param email user given email
     * @param password user given password
     * @return recently authenticated user
     */
    private Authentication authenticate(String email, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException authenticationException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    authenticationException.getMessage(), authenticationException);
        }
    }

}
