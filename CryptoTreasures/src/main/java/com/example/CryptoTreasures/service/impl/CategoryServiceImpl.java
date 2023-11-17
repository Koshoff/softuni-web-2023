package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.repository.CategoryRepository;
import com.example.CryptoTreasures.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
