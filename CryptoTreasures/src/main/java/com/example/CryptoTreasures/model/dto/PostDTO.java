package com.example.CryptoTreasures.model.dto;

import com.example.CryptoTreasures.model.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class PostDTO {

    private Long id;
    @NotBlank
    @Size(min=5, max=100)
    private String title;
    @NotBlank
    @Size(min=20, max=500)
    private String content;
    private Long authorId;
    private List<CommentDTO> comments;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateCreated;
    private String authorUsername;



    public PostDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate publicationDate) {
        this.dateCreated = publicationDate;
    }
}
