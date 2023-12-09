package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/crypto-card")
public class CryptoCardController {

    private final CryptoCardService cryptoCardService;

    public CryptoCardController(CryptoCardService cryptoCardService) {
        this.cryptoCardService = cryptoCardService;
    }


    @GetMapping("/{id}")
    public ModelAndView cryptoCardDetails(@PathVariable("id") Long cardId) {
        CryptoCardDTO cryptoCardDTO = cryptoCardService.findById(cardId);
        ModelAndView modelAndView = new ModelAndView("crypto-card-details");
        modelAndView.addObject("card", cryptoCardDTO);
        return modelAndView;
    }
}
