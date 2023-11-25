package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

     void saveArticle(AddArticleModel addArticleModel);


     Page<ArticleDTO> getAllArticles(Pageable pageable);

     Page<ArticleDTO> getArticlesByCategory(String category, Pageable pageable);

     ArticleDTO findById(Long id);

     List<ArticleDTO> findUnapprovedArticles();
     List<ArticleDTO> findByArticleStatus(ArticleStatus articleStatus);

     void approveArticle(ArticleDTO article);

     void rejectArticle(Long articleId, String reasonMessage);

     List<ArticleDTO> findByAuthor(UserDTO author);


     List<ArticleDTO> getMostLikedArticles();

     void deleteArticle(Long articleId);
}
