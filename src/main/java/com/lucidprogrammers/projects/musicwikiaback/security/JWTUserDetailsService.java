package com.lucidprogrammers.projects.musicwikiaback.security;

import com.lucidprogrammers.projects.musicwikiaback.database.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.lucidprogrammers.projects.musicwikiaback.database.entity.User user = userFacade.findByEmail(s);

        List<String> roles = Arrays.asList(user.getRole().split(";"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
