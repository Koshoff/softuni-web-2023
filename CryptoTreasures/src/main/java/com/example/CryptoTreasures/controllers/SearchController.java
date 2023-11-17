package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping
    public ResponseEntity<List<Article>> searchResults(@RequestParam String keyword) {
        List<Article> articles = searchService.searchByKeyword(keyword);

        return ResponseEntity.ok(articles);

    }
}
