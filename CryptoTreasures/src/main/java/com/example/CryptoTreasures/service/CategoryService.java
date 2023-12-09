package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.CategoryDTO;
import com.example.CryptoTreasures.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();
}
