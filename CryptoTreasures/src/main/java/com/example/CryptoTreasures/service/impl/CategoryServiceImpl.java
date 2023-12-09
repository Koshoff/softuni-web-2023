package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CategoryDTO;
import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.repository.CategoryRepository;
import com.example.CryptoTreasures.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryServiceImpl::form)
                .collect(Collectors.toList());
    }

    private static CategoryDTO form(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }
}
