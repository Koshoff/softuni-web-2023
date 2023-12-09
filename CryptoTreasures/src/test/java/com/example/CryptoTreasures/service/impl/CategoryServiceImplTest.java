package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CategoryDTO;
import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    @Mock
    private  CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {

        // Подготовка на данни за мокнатия репозитори


        List<Category> categories = Arrays.asList(
                new Category("name1", "Category 1"),
                new Category("name2", "Category 2")
        );

        // Конфигуриране на мокнатия репозитори
        when(categoryRepository.findAll()).thenReturn(categories);

        // Извикване на метода на сервиса, който искате да тествате
        List<CategoryDTO> result = categoryService.findAll();

        // Проверка на резултата
        assertEquals(2, result.size()); // Проверка за броя на върнатите категории
        assertEquals("name1", result.get(0).getName()); // Проверка на името на първата категория
        assertEquals("name2", result.get(1).getName());
    }
}