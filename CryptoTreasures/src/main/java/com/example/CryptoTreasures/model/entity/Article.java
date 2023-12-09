package com.example.CryptoTreasures.model.entity;

import com.example.CryptoTreasures.model.enums.ArticleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="articles")
public class Article extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 65535)
    @Lob
    private String content;
    @Column(name="publication_date")
    private LocalDate publicationDate;
    @ManyToOne
    private User author;
    @Column(nullable = false)
    private Boolean isApproved;
    @ManyToOne
    private Category category;
    @Column(name="thumbnail_url")
    private String thumbnailUrl;
    @Column(name="article_status")
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;
    private String message;

    public Article() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }


    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategories(Category category) {
        this.category = category;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
