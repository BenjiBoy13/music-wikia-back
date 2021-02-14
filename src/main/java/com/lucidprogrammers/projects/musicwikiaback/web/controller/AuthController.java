package com.lucidprogrammers.projects.musicwikiaback.web.controller;

import com.lucidprogrammers.projects.musicwikiaback.app.service.UserService;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.MusicWikiaResponse;
import com.lucidprogrammers.projects.musicwikiaback.web.model.response.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MusicWikiaResponse<UserResponseModel> login(@RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = userService.login(userRequestModel);
        return new MusicWikiaResponse<>(
                "Login with success", "success", 200, userResponseModel
        );
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MusicWikiaResponse<UserResponseModel> register(@RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel= userService.createUser(userRequestModel);
        return new MusicWikiaResponse<>(
                "User created with success", "success", 201, userResponseModel
        );
    }

}
