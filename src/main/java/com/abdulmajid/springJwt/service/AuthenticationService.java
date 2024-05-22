package com.abdulmajid.springJwt.service;

import com.abdulmajid.springJwt.model.AuthenticationResponse;
import com.abdulmajid.springJwt.model.Token;
import com.abdulmajid.springJwt.model.User;
import com.abdulmajid.springJwt.repository.TokenRepository;
import com.abdulmajid.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepository tokenRepository;

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());
        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        //save the generaterd token in db
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt);
    }



    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        revokeLlTokenByUser(user);
        saveUserToken(token,user);
        return new AuthenticationResponse(token);
    }

    private void revokeLlTokenByUser(User user) {
        List<Token> validTokenListByUser=tokenRepository.findAllTokenByUser(user.getId());
        if (!validTokenListByUser.isEmpty())
        {
            validTokenListByUser.forEach(t->{
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(String jwt, User user) {
        Token token=new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

}





