package com.lucidprogrammers.projects.musicwikiaback.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ApiController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String hello() {
        return "Hello";
    }

}
