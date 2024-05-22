package com.abdulmajid.springJwt.controller;

import com.abdulmajid.springJwt.model.AuthenticationResponse;
import com.abdulmajid.springJwt.model.User;
import com.abdulmajid.springJwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody User request)
    {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public  AuthenticationResponse login(@RequestBody User request)
    {
        return  authenticationService.authenticate(request);
    }
}
