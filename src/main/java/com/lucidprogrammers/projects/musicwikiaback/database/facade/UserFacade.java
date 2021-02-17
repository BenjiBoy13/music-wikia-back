package com.lucidprogrammers.projects.musicwikiaback.database.facade;

import com.lucidprogrammers.projects.musicwikiaback.database.entity.User;
import com.lucidprogrammers.projects.musicwikiaback.database.repository.UserRepository;
import com.lucidprogrammers.projects.musicwikiaback.util.Constants;
import com.lucidprogrammers.projects.musicwikiaback.web.model.request.UserRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(UserRequestModel userRequestModel) {
        return userRepository.save(userRequestToUserEntity(userRequestModel));
    }

    private User userRequestToUserEntity(UserRequestModel userRequestModel) {
        User user = new User();
        user.setFullName(userRequestModel.getFullName());
        user.setUsername(userRequestModel.getUsername());
        user.setEmail(userRequestModel.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userRequestModel.getPassword()));
        user.setCreatedDate(new Date());
        user.setStatus(Constants.USER_STATUS_ACTIVE);
        user.setRole(Constants.ROL_USER);

        return user;
    }

}
