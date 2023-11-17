package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.events.UserRegisterEvent;
import com.example.CryptoTreasures.service.UserActivationService;
import jakarta.validation.constraints.Email;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {
    @EventListener(UserRegisterEvent.class)
    @Override
    public void userRegistered(UserRegisterEvent event) {
        System.out.println("User with username "+ event.getUsername());
    }
}
