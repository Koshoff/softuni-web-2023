package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.entity.Article;

import java.util.List;

public interface SearchService {


    List<Article> searchByKeyword(String keyword);
}
