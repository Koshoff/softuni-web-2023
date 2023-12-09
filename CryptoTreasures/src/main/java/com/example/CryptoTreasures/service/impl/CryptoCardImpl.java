package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.model.entity.CryptoCard;
import com.example.CryptoTreasures.repository.CryptoCardRepository;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoCardImpl implements CryptoCardService {

    private final CryptoCardRepository cryptoCardRepository;

    private final ModelMapper modelMapper;

    public CryptoCardImpl(CryptoCardRepository cryptoCardRepository, ModelMapper modelMapper) {
        this.cryptoCardRepository = cryptoCardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void saveCard(CryptoCardDTO cryptoCardDTO) {
        CryptoCard cryptoCard = modelMapper.map(cryptoCardDTO, CryptoCard.class);
        cryptoCardRepository.save(cryptoCard);

    }

    @Override
    public Page<CryptoCardDTO> findAllCards(Pageable pageable) {
        Page<CryptoCard> cryptoCards = cryptoCardRepository.findAll(pageable);
        return cryptoCards.map(cryptoCard -> modelMapper.map(cryptoCard, CryptoCardDTO.class));
    }

    @Override
    public CryptoCardDTO findById(Long id) {
        return modelMapper.map(cryptoCardRepository.findById(id), CryptoCardDTO.class);
    }

}
