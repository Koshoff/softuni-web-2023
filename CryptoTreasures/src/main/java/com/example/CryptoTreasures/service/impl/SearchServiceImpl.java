package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ArticleRepository articleRepository;

    public SearchServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> searchByKeyword(String keyword) {
        return articleRepository.findByContentContainingIgnoreCase(keyword);
    }
}
