package com.example.CryptoTreasures.restcontrollers;

import com.example.CryptoTreasures.model.dto.RatingDTO;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class RatingRestController {

    private final RatingService ratingService;

    public RatingRestController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @PostMapping("/like-article/{articleId}")
    public ResponseEntity<RatingDTO> likeArticle(@PathVariable Long articleId, Principal principal) {
            ratingService.likeArticle(articleId, principal.getName());
            return ResponseEntity.ok().build();


    }
}
