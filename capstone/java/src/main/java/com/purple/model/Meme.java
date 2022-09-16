package com.purple.model;

import java.time.LocalDate;

public class Meme {
    private Long memeId;
    private Long imageId;
    private Long authorId;
    private String author;
    private LocalDate uploadDate;
    private Long popularity;
    private boolean active;
    private boolean approved;
    private boolean favorite;
    private String color;
    private Integer fontSize;

    public Meme() {
        this.favorite = false;
    }

    public Meme(Long memeId, Long imageId, Long authorId, String author, LocalDate uploadDate, Long popularity, boolean active, boolean approved, boolean favorite, Integer fontSize, String color) {
        this.memeId = memeId;
        this.imageId = imageId;
        this.authorId = authorId;
        this.author = author;
        this.uploadDate = uploadDate;
        this.popularity = popularity;
        this.active = active;
        this.approved = approved;
        this.favorite = favorite;
        this.color = color;
        this.fontSize = fontSize;
    }

    public Long getMemeId() {
        return memeId;
    }

    public void setMemeId(Long memeId) {
        this.memeId = memeId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
}
