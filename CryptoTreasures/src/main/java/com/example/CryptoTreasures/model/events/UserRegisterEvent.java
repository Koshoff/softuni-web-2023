package com.example.CryptoTreasures.model.events;

import org.springframework.context.ApplicationEvent;
//Първа стъпка в създаването на Event
public class UserRegisterEvent extends ApplicationEvent {

    private final String username;
    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
