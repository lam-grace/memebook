package com.purple.dao;

import com.purple.model.Image;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ImageDao {
    Image getImageById(Long id);
    public Image getImageByMemeId(Long memeId);
    public Image uploadImage(Image image);
    List<Image> getAllImages();
    public List<Image> filterImagesByTag(String tagName);
    public List<Image> filterImagesByAuthor(Long authorId);
    public List<Image> getNewestImages();
    public List<Image> getPopularImages();
    public Image deleteImage(Long id);
    public void likeImage(Long imageId, Long personId);
    public List<Image> getAllLikedImagesByUser(Long personID);
    Image mapRowToImage(SqlRowSet results);

}