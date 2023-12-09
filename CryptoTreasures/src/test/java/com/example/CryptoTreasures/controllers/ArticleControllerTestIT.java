package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CategoryDTO;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.CategoryService;
import com.example.CryptoTreasures.service.CommentService;
import com.example.CryptoTreasures.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  ArticleService articleService;
    @MockBean
    private  CategoryService categoryService;
    @MockBean
    private  CommentService commentService;
    @MockBean
    private  UserService userService;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testLoadDefiArticles() throws Exception {

        Pageable pageable = PageRequest.of(0, 4);
        List<ArticleDTO> defiArticles = Arrays.asList(new ArticleDTO(), new ArticleDTO());

        // Mock the articleService to return the list of DeFi articles
        when(articleService.getArticlesByCategory("DeFi", pageable)).thenReturn(new PageImpl<>(defiArticles, pageable, defiArticles.size()));

        mockMvc.perform(get("/article/defi"))
                .andExpect(status().isOk())
                .andExpect(view().name("defi"))
                .andExpect(model().attributeExists("DeFiArticles"))
                .andExpect(model().attribute("DeFiArticles", defiArticles));

    }

    @Test
    void loadNFTArticles() {
    }

    @Test
    void loadCryptocurrencyArticles() {
    }

    @Test
    @WithMockUser(username = "testUser")
    void testCreateArticleGet() throws Exception {
        List<CategoryDTO> mockCategories = List.of(new CategoryDTO());
        when(categoryService.findAll()).thenReturn(mockCategories);
        mockMvc.perform(get("/article/create-article"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-article"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", mockCategories));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testCreate() throws Exception{


        List<CategoryDTO> categories = Collections.emptyList();

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/article/create-article"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Очакваме HTTP статус 200 (OK)
                .andExpect(MockMvcResultMatchers.view().name("create-article")) // Очакваме изгледът да е "create-article"
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories")) // Очакваме да има модел атрибут "categories"
                .andExpect(MockMvcResultMatchers.model().attribute("categories", categories));
    }

    @Test
    void deleteArticle() {
    }




}