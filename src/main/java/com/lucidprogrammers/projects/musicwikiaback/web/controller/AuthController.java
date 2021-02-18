package com.lucidprogrammers.projects.musicwikiaback.web.controller;

import com.lucidprogrammers.projects.musicwikiaback.app.service.UserService;
import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.MusicWikiaResponse;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.TokenResponseModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * API controller regarding authorization
 * operations
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String RESPONSE_MESSAGE_USER_LOGGED = "Logged in with success";

    private static final String RESPONSE_MESSAGE_USER_CREATED = "User created with success";

    private final UserService userService;

    /**
     * Authenticates a user via user email and
     * password
     *
     * @param userRequestModel HTTP request user data
     * @return Music Wikia HTTP base response with {@link TokenResponseModel} data
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MusicWikiaResponse<TokenResponseModel> login(@RequestBody UserRequestModel userRequestModel) {
        TokenResponseModel tokenResponseModel = userService.login(userRequestModel);
        return new MusicWikiaResponse<>(
                RESPONSE_MESSAGE_USER_LOGGED,
                Constants.STATUS_TYPE_SUCCESS,
                Constants.STATUS_CODE_ACCEPTED,
                tokenResponseModel
        );
    }

    /**
     * Creates a user with default authorities of ROLE_USER
     * and status 'A' (active)
     *
     * @param userRequestModel HTTP request user data
     * @return Music Wikia HTTP base response with {@link UserResponseModel} data
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MusicWikiaResponse<UserResponseModel> register(@RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel= userService.createUser(userRequestModel);
        return new MusicWikiaResponse<>(
                RESPONSE_MESSAGE_USER_CREATED,
                Constants.STATUS_TYPE_SUCCESS,
                Constants.STATUS_CODE_CREATED,
                userResponseModel
        );
    }

}
