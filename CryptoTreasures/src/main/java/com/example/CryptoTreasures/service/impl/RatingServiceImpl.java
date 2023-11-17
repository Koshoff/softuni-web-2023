package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.RatingRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void likeArticle(Long articleId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        Article article = articleRepository.findById(articleId).orElse(null);
        if(user != null && article != null && !ratingRepository.existsByRaterAndArticle(user, article)) {
            Rating rating = new Rating();
            rating.setArticle(article);
            rating.setRating(true);
            rating.setRater(user);
            ratingRepository.save(rating);
        }

    }

    @Override
    public boolean ratingExistsByUserAndArticle(User user, Article article) {
        return ratingRepository.existsByRaterAndArticle(user,article);
    }
}
