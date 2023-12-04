package com.example.CryptoTreasures.exceptions;

public class EntityNotFoundException extends RuntimeException{

    private Long id;

    public EntityNotFoundException(String message, Long entityId) {
        super(message);
        this.id = entityId;
    }
}
