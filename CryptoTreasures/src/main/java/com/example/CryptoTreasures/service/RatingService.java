package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;

public interface RatingService {

    void likeArticle(Long articleId, String username) ;

    boolean ratingExistsByUserAndArticle(User user, Article article);
}
