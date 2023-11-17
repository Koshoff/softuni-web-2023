package com.example.CryptoTreasures.repository;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Rating;
import com.example.CryptoTreasures.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByRaterAndArticle(User rater, Article article);
}
