package com.abdulmajid.springJwt.config;

import com.abdulmajid.springJwt.model.Token;
import com.abdulmajid.springJwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        //get stored token from database

        Token storedToken=tokenRepository.findByToken(token).orElse(null);

        //invalidate the token i,e make logout true

        if (token!=null)
        {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }

    }
}
