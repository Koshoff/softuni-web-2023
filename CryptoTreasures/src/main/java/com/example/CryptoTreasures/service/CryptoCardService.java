package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CryptoCardService {

    void saveCard(CryptoCardDTO cryptoCardDTO);

    Page<CryptoCardDTO> findAllCards(Pageable pageable);

    CryptoCardDTO findById(Long id);
}
