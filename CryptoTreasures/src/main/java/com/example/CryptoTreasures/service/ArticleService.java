package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

     void saveArticle(AddArticleModel addArticleModel);

     //TODO : да заместя Article с DTO
     Page<Article> getAllArticles(Pageable pageable);

     Page<Article> getArticlesByCategory(String category, Pageable pageable);

     Article findById(Long id);

     List<Article> findUnapprovedArticles();

     void approveArticle(Article article);

     List<Article> findByAuthor(User author);



}
