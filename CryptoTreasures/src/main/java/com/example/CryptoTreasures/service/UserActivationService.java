package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.events.UserRegisterEvent;

public interface UserActivationService {

    void userRegistered(UserRegisterEvent event);
}
