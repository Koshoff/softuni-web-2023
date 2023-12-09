package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.model.entity.CryptoCard;
import com.example.CryptoTreasures.repository.CryptoCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CryptoCardImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CryptoCardRepository cryptoCardRepository;

    @InjectMocks
    private CryptoCardImpl cryptoCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCard() {

        CryptoCardDTO cryptoCardDTO = new CryptoCardDTO();
        cryptoCardDTO.setInformation("testInfo");
        cryptoCardDTO.setName("testName");
        cryptoCardDTO.setImageUrl("testUrl");
        CryptoCard cryptoCard = new CryptoCard();

        when(modelMapper.map(cryptoCardDTO, CryptoCard.class)).thenReturn(cryptoCard);

        cryptoCardService.saveCard(cryptoCardDTO);

        verify(cryptoCardRepository).save(cryptoCard);
    }

    @Test
    void findAllCards() {
    }

    @Test
    void findByIdTest() {

        Long id = 1L;
        CryptoCard cryptoCard = new CryptoCard();
        cryptoCard.setId(id);

        when(cryptoCardRepository.findById(id)).thenReturn(Optional.of(cryptoCard));
        when(modelMapper.map(cryptoCard, CryptoCardDTO.class)).thenReturn(new CryptoCardDTO());

        CryptoCardDTO result = cryptoCardService.findById(id);

        assertNotNull(result);
    }



}