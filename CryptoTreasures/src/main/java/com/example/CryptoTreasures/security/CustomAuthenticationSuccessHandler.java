package com.example.CryptoTreasures.security;

import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (authentication.getAuthorities().contains((new SimpleGrantedAuthority("ROLE_BANNED")))){

            response.sendRedirect(request.getContextPath() + "/banned");
        }
        else{
            response.sendRedirect(request.getContextPath() + "/about");
        }
    }
}
