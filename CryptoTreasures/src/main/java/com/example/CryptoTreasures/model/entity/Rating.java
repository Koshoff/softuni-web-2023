package com.example.CryptoTreasures.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="ratings")
public class Rating extends BaseEntity {
    @Column(nullable = false)
    private Boolean rating;
    @ManyToOne
    @JoinColumn(name="rater_id")
    private User rater;
    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    public Rating() {
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
