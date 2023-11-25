package com.example.CryptoTreasures.repository;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    Page<Article> findByCategoryName(String name, Pageable pageable);

    List<Article> findByIsApprovedFalse();

    List<Article> findByArticleStatus(ArticleStatus articleStatus);

    List<Article> findByContentContainingIgnoreCase(String keyword);

    List<Article> findByAuthor(User author);
    @Query("SELECT r.article, COUNT(r) as likesCount FROM Rating r WHERE r.rating = true GROUP BY r.article ORDER BY likesCount ASC")
    List<Article> findMostLikedArticlesAscending();




}
