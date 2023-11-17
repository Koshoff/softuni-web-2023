package com.example.CryptoTreasures.restcontrollers;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.RatingService;
import com.example.CryptoTreasures.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/rate")
public class RatingRestController {

    private final UserService userService;
    private final ArticleService articleService;
    private final RatingService ratingService;

    public RatingRestController(UserService userService, ArticleService articleService, RatingService ratingService) {
        this.userService = userService;
        this.articleService = articleService;
        this.ratingService = ratingService;
    }

    @PostMapping("/article/{articleId}/like")
    @ResponseBody
    public ResponseEntity<Rating> vote(@PathVariable(name = "articleId") Long articleId, Principal principal) {
        String username = principal.getName();
        ratingService.likeArticle(articleId, username);
        return ResponseEntity.ok().build();

    }


}
