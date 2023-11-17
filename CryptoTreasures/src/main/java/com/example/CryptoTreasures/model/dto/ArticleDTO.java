package com.example.CryptoTreasures.model.dto;

import com.example.CryptoTreasures.model.entity.Rating;

import java.time.LocalDate;
import java.util.List;

public class ArticleDTO {
    private Long id;

    private String title;
    private String content;

    private LocalDate publicationDate;
    private String authorName;  // предполагам, че може би искате само името на автора, а не цялата информация
    private Boolean isApproved;
    private String categoryName;  // също така предполагам, че може би искате само името на категорията
    private List<CommentDTO> comments;

    public ArticleDTO(){

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
