package com.example.CryptoTreasures.Interceptors;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.
                getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BANNED")))
        {
            response.sendRedirect(request.getContextPath() +"/banned");

                return false;
            }
        return true;
    }


}
