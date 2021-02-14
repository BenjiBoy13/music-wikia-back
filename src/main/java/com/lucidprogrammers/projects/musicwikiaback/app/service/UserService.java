package com.lucidprogrammers.projects.musicwikiaback.app.service;

import com.lucidprogrammers.projects.musicwikiaback.database.entity.User;
import com.lucidprogrammers.projects.musicwikiaback.database.facade.UserFacade;
import com.lucidprogrammers.projects.musicwikiaback.security.JWTTokenUtil;
import com.lucidprogrammers.projects.musicwikiaback.security.JWTUserDetailsService;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFacade userFacade;

    private final JWTTokenUtil jwtTokenUtil;

    private final JWTUserDetailsService jwtUserDetailsService;

    private final AuthenticationManager authenticationManager;

    public UserResponseModel login(UserRequestModel userRequestModel) {
        authenticate(userRequestModel.getEmail(), userRequestModel.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userRequestModel.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new UserResponseModel(null, null, null, null, null, token);
    }

    public UserResponseModel createUser(UserRequestModel userRequestModel) {
        User user = userFacade.createUser(userRequestModel);
        return userEntityToUserResponse(user);
    }

    private UserResponseModel userEntityToUserResponse(User user) {
        return new UserResponseModel(
                user.getFullName(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedDate(),
                null
        );
    }

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
