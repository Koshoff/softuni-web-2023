package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.RatingDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.RatingService;
import com.example.CryptoTreasures.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {

        this.ratingService = ratingService;
    }


    @PostMapping("/like-article/${articleId}")
    public ResponseEntity<Rating> likeArticle(@PathVariable Long articleId, Principal principal) {
            ratingService.likeArticle(articleId, principal.getName());
            return ResponseEntity.ok().build();


    }
}
