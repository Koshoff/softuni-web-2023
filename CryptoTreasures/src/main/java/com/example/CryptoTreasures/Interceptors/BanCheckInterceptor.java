package com.example.CryptoTreasures.Interceptors;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
public class BanCheckInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public BanCheckInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Principal principal = request.getUserPrincipal();
        if(principal != null) {
            String username =request.getUserPrincipal().getName();
            UserDTO user = userService.findByUsername(username);

            if(user != null && user.getRole().name().equals("BANNED")) {
                response.sendRedirect("/banned");
                return false;
            }
        }
        return true;
    }
}
