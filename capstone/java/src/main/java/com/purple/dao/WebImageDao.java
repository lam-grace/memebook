package com.purple.dao;

import com.purple.model.WebImage;

import java.util.List;

public interface WebImageDao {
    WebImage getWebImage(Long id);

    List<WebImage> getWebImages();

    WebImage saveWebImage(WebImage webImage);

    void deleteWebImage(Long id);
}
