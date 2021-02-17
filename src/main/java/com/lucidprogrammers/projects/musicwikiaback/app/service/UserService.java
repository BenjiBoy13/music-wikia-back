package com.lucidprogrammers.projects.musicwikiaback.app.service;

import com.lucidprogrammers.projects.musicwikiaback.database.entity.User;
import com.lucidprogrammers.projects.musicwikiaback.database.facade.UserFacade;
import com.lucidprogrammers.projects.musicwikiaback.util.JWTTokenUtil;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username was not found");

        List<String> roles = Arrays.asList(user.getRole().split(";"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    /**
     * Authenticates the user and returns the
     * generated token
     *
     * @param userRequestModel HTTP request containing user data
     * @return HTTP response containing user token
     */
    public UserResponseModel login(UserRequestModel userRequestModel) {
        authenticate(userRequestModel.getEmail(), userRequestModel.getPassword());

        final UserDetails userDetails = loadUserByUsername(userRequestModel.getEmail());

        final String token = JWTTokenUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

        return new UserResponseModel(null, null, null, null, null, token);
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
                user.getFullName(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedDate(),
                null
        );
    }

    /**
     * Authenticates the user with spring
     * security context
     *
     * @param email user given email
     * @param password user given password
     */
    private void authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException disabledException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Inactive user");
        } catch (BadCredentialsException badCredentialsException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid");
        }
    }

}
