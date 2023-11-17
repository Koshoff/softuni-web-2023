package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.entity.Category;

import java.util.List;

public interface CategoryService {
    //TODO : да заместя Category с DTO
    List<Category> findAll();
}
