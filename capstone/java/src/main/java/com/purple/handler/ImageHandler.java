package com.purple.handler;

import com.purple.dao.ImageDao;
import com.purple.dao.MemeDao;
import com.purple.dao.MemeTextDao;
import com.purple.dao.TagDao;
import com.purple.model.*;
import com.purple.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageHandler implements ImageHandlerable {
    private final ImageDao imageDao;
    private final MemeDao memeDao;
    private final MemeTextDao memeTextDao;
    private final TagDao tagDao;

    @Autowired
    public ImageHandler(ImageDao imageDao, MemeDao memeDao, MemeTextDao memeTextDao, TagDao tagDao){
        this.imageDao = imageDao;
        this.memeDao = memeDao;
        this.memeTextDao = memeTextDao;
        this.tagDao = tagDao;
    }

    // overlay the text over an image and output a meme
    public Image getWebImageWithMeme(Meme meme){ // replace this with Meme meme object
        List<MemeText> textList = getAllTextsByMeme(meme);
        //get imag by sourceImage ID from meme table
        Image baseImage = imageDao.getImageByMemeId(meme.getMemeId());
        InputStream targetStream = new ByteArrayInputStream(baseImage.getFileBlob());
        BufferedImage image = null;
        try {
            image = ImageIO.read(targetStream);
            //get font info and color from meme table
            Font font = new Font("Arial", Font.BOLD, meme.getFontSize());
            Color color = Color.decode(meme.getColor());
            for (MemeText memeText : textList) {
                Graphics g = image.getGraphics();
                // get locations form memeText table
                String text = memeText.getMemeText();
                int posX = (int) Math.round((double)(image.getWidth()) * (double) (memeText.getxPos()) / 100.0);
                int posY = (int) Math.round((double)(image.getHeight()) * (double)(memeText.getyPos()) / 100.0 + (double)(meme.getFontSize()));

                AttributedString attributedText = new AttributedString(text);
                attributedText.addAttribute(TextAttribute.FONT, font);
                attributedText.addAttribute(TextAttribute.FOREGROUND, color);
                g.drawString(attributedText.getIterator(), posX, posY);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            baseImage.setFileBlob(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baseImage;  // this is the generated meme
    }

    //save image to the database. Insert into the database
    @Override
    public Image uploadImage(Image image){
        return imageDao.uploadImage(image);
    }

    // retrieves an image from the database
    @Override
    public Image getImage(Long id){
        return imageDao.getImageById(id);
    }

    @Override
    public Image getImageByMemeId(Long id){
        return imageDao.getImageByMemeId(id);
    }

    @Override
    public List<Image> getAllImages() {
        return imageDao.getAllImages();
    }

    @Override
    public List<Image> filterImagesByTag(String tagName) {
        return imageDao.filterImagesByTag(tagName);
    }

    @Override
    public List<Image> filterImagesByAuthor(User author) {
        return imageDao.filterImagesByAuthor(author.getUserId());
    }

    @Override
    public List<Image> getNewestImages() {
        return imageDao.getNewestImages();
    }

    @Override
    public List<Image> getPopularImages() {
        return imageDao.getPopularImages();
    }

    @Override
    public Image deleteImage(Long id) {
        memeDao.deleteMemesByImageId(id);
        return imageDao.deleteImage(id);
    }

    @Override
    public void likeImage(Long imageId, Long personId)  { imageDao.likeImage(imageId,personId);}

    @Override
    public List<Image> getAllLikedImagesByUser(Long personID) {return imageDao.getAllLikedImagesByUser(personID);}

    @Override
    public List<MemeText> getAllTextsByMeme(Meme meme) {
        return memeTextDao.getAllTextsByMemeId(meme.getMemeId());
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    @Override
    public List<Tag> getAllTagsNotImageId (Long id) {
        return tagDao.getAllTagsNotImageId(id);
    }

    @Override
    public List<Tag> getTagsByImageId(Long imageId) {
        return tagDao.getAllTagsByImageId(imageId);
    }

    @Override
    public Image assignTagToImage(Long imageId, Long tagId) {
        tagDao.assignTagToImage(imageId, tagId);
        return imageDao.getImageById(imageId);
    }

    @Override
    public Image removeTagFromImage(Long imageId, Long tagId) {
        tagDao.removeTagFromImage(imageId, tagId);
        return imageDao.getImageById(imageId);
    }

    @Override
    public Tag createTag(String tagName, Long imageId) {
        Tag tag = tagDao.createTag(tagName);
        assignTagToImage(imageId, tag.getTagId());
        return tag;
    }

}
