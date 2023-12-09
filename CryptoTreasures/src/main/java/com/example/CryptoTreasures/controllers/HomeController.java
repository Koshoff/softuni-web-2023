package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.configuration.ScheduledTasks;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final CryptoCardService cryptoCardService;


    public HomeController(CryptoCardService cryptoCardService) {
        this.cryptoCardService = cryptoCardService;


    }

    @GetMapping("/index")
    public ModelAndView homePage(@PageableDefault(size=6, sort="id") Pageable pageable){
        Page<CryptoCardDTO> cryptoCards = cryptoCardService.findAllCards(pageable);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("cryptoCards", cryptoCards);

        return modelAndView;
    }



    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return  new ModelAndView("about");
    }


}
