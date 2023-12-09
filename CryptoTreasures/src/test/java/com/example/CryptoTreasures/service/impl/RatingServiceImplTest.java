package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.RatingRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RatingServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void likeArticle() {
        Long articleId = 1L;
        String username = "testuser";

        User user = new User();
        Article article = new Article();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(ratingRepository.existsByRaterAndArticle(user, article)).thenReturn(false);
        Rating rating = new Rating();
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);


        ratingService.likeArticle(articleId, username);

        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void ratingExistsByUserAndArticle() {

        User user = new User();
        Article article = new Article();
        when(ratingRepository.existsByRaterAndArticle(user, article)).thenReturn(false);


        boolean result = ratingService.ratingExistsByUserAndArticle(user, article);


        assertFalse(result);
    }
}