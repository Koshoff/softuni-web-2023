package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.CryptoCardService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

    private final CryptoCardService cryptoCardService;

    private final ArticleService articleService;

    public ModeratorController(CryptoCardService cryptoCardService, ArticleService articleService) {
        this.cryptoCardService = cryptoCardService;
        this.articleService = articleService;
    }


    @GetMapping("/articles-approve")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView showUnapprovedArticles() {
        List<ArticleDTO> unapprovedArticles = articleService.findByArticleStatus(ArticleStatus.PENDING);
        ModelAndView model = new ModelAndView("articles-approve");
        model.addObject("unapprovedArticles", unapprovedArticles);
        return model;
    }


    @PostMapping("/approve/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView approveArticle(@PathVariable("id") Long articleId) {
        ArticleDTO article = articleService.findById(articleId);
        articleService.approveArticle(articleId);
        return new ModelAndView("redirect:/moderator/articles-approve");
    }

    @PostMapping("/reject/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView rejectArticle(@PathVariable("id") Long articleId, @RequestParam String rejectionReason) {
        ArticleDTO article = articleService.findById(articleId);
        articleService.rejectArticle(articleId, rejectionReason);
        return new ModelAndView("redirect:/moderator/articles-approve");
    }


    @GetMapping("/make-crypto-card")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView cryptoCard() {
        return new ModelAndView("crypto-card");
    }


    @PostMapping("/make-crypto-card")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView cryptoCard(@ModelAttribute("cryptoCardDTO") @Valid CryptoCardDTO cryptoCardDTO,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("crypto-card");
            modelAndView.addObject("cryptoCardDTO", cryptoCardDTO);
            return modelAndView;
        }
        cryptoCardService.saveCard(cryptoCardDTO);

        return new ModelAndView("redirect:/moderator/articles-approve");
    }
}
