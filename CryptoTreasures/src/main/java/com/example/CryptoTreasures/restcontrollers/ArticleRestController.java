package com.example.CryptoTreasures.restcontrollers;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/most-liked")
    public List<ArticleDTO> getMostLikedArticles() {
        return articleService.getMostLikedArticles();
    }
}
