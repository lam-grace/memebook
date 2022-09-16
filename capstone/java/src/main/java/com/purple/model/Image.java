package com.purple.model;

import java.time.LocalDate;

public class Image {
    private Long imageId;
//    private String imageReference;
    private Long fileSize;
    private byte[] fileBlob;
    private String mimeType;
    private LocalDate dateUploaded;
    private Long popularity;
    private boolean active;

    public Image() {
    }

    public Image(Long imageId, Long fileSize, byte[] fileBlob, LocalDate dateUploaded, Long popularity, boolean active) {
        this.imageId = imageId;
        this.fileSize = fileSize;
        this.fileBlob = fileBlob;
        this.dateUploaded = dateUploaded;
        this.popularity = popularity;
        this.active = active;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(byte[] fileBlob) {
        this.fileBlob = fileBlob;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LocalDate getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDate dateUploaded) {
        this.dateUploaded = dateUploaded;
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
}
