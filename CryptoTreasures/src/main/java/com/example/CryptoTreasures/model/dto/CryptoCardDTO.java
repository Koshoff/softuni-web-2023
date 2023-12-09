package com.example.CryptoTreasures.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CryptoCardDTO {

    private Long id;
    @NotBlank
    @Length(min = 5, message = "Name must be minimum 5 characters")
    private String name;

    @Length(min = 20, message = "Content must be minimum 200 characters")
    private String information;
    @NotBlank(message = "Image url is required")
    private String imageUrl;

    public CryptoCardDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
