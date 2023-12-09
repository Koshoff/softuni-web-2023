package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoCardService cryptoCardService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void homePage() throws Exception {
        List<CryptoCardDTO> mockCryptoCards = Arrays.asList(new CryptoCardDTO(), new CryptoCardDTO());
        Page<CryptoCardDTO> mockCryptoCardsPage = new PageImpl<>(mockCryptoCards);

        when(cryptoCardService.findAllCards(any(Pageable.class))).thenReturn(mockCryptoCardsPage);

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("cryptoCards"))
                .andExpect(model().attribute("cryptoCards", mockCryptoCardsPage));

    }

    @Test
    void aboutPage() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}