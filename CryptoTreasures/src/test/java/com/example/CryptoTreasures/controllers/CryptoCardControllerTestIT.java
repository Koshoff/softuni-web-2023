package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class CryptoCardControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoCardService cryptoCardService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cryptoCardDetails() throws Exception{

        Long cardId = 1L;
        CryptoCardDTO mockCryptoCardDTO = new CryptoCardDTO();



        Mockito.when(cryptoCardService.findById(cardId)).thenReturn(mockCryptoCardDTO);

        mockMvc.perform(get("/crypto-card/" + cardId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("card"))
                .andExpect(view().name("crypto-card-details"));
    }
}