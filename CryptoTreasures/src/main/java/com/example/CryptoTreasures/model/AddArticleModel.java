package com.example.CryptoTreasures.model;

import com.example.CryptoTreasures.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddArticleModel {
    @NotBlank
    @Size(min=10, max=150, message = "The title must be at least 10 characters")
    private String title;
    @NotBlank
    @Size(min=150, max = 2000, message = "The content must be at least 150 characters")
    private String content;
    @NotNull
    private Category category;

    private String thumbnailUrl;

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

    public Category getCategory() {
        return category;
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
