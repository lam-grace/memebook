package com.purple.handler;

import com.purple.model.*;

import java.util.List;

public interface ImageHandlerable {
    Image getImage(Long id);
    Image getImageByMemeId(Long id);
    List<Image> getAllImages();
    public List<Image> filterImagesByTag(String tagName);
    public List<Image> filterImagesByAuthor(User author);
    public List<Image> getNewestImages();
    public List<Image> getPopularImages();
    public Image deleteImage(Long id);
    Image getWebImageWithMeme(Meme meme);
    Image uploadImage(Image image);
    public List<MemeText> getAllTextsByMeme(Meme meme);
    public void likeImage(Long imageId, Long personId);
    public List<Image> getAllLikedImagesByUser(Long personID);
    public List<Tag> getAllTags();
    public List<Tag> getAllTagsNotImageId (Long id);
    public List<Tag> getTagsByImageId(Long imageId);
    public Image assignTagToImage(Long imageId, Long tagId);
    public Tag createTag(String tagName, Long imageId);
    public Image removeTagFromImage(Long imageId, Long tagId);
}
