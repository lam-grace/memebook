package com.purple.handler;

import com.purple.dao.ImageDao;
import com.purple.dao.MemeDao;
import com.purple.model.*;
import com.purple.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemeHandler implements MemeHandlerable {
    private final MemeDao memeDao;
    private final ImageDao imageDao;

    @Autowired
    public MemeHandler(MemeDao memeDao, ImageDao imageDao){
        this.memeDao = memeDao;
        this.imageDao = imageDao;
    }

    @Override
    public Meme getMemeById(Long id, Long currentUserId) {
        Meme meme = memeDao.getMemeById(id);
        meme.setFavorite(memeDao.setFavourite(id, currentUserId));
        return meme;
    }

    @Override
    public List<Meme> getAllMemes() {
        return memeDao.getAllMemes();
    }

    @Override
    public List<Meme> getAllUnapprovedMemes() {
        return memeDao.getAllUnapprovedMemes();
    }

    @Override
    public List<Meme> getTopMemes(Integer number) {
        return memeDao.getTopMemes(number);
    }

    @Override
    public List<Meme> filterMemesBySourceImageId(User author) {
        return memeDao.filterMemesBySourceImageId(author.getUserId());
    }

    @Override
    public List<Meme> filterMemesByAuthor(Long userId) {
        return memeDao.filterMemesByAuthor(userId);
    }

    @Override
    public List<Meme> filterMemesByWord(String word) {
        return memeDao.filterMemesByWord(word);
    }

    @Override
    public List<Meme> filterMemesByTag(String tag) {
        return memeDao.filterMemesByTag(tag);
    }

    @Override
    public List<Meme> searchMemeByWordOrTag(String word) {
        return memeDao.searchMemeByWordOrTag(word);
    }

    @Override
    public List<Meme> getNewestMemes() {
        return memeDao.getNewestMemes();
    }

    @Override
    public List<Meme> getPopularMemes() {
        return memeDao.getPopularMemes();
    }

    @Override
    public List<Meme> filterMemesByFavourites(Long userId) {
        return memeDao.filterMemesByFavourites(userId);
    }

    @Override
    public Meme reportMeme(Long id) {
        return memeDao.reportMeme(id);
    }

    @Override
    public Meme deleteMeme(Long id) {
        return memeDao.deleteMeme(id);
    }

    public Meme approveMeme(Long id) {
        return memeDao.approveMeme(id);
    }

    @Override
    public Meme likeMeme(Long memeId, Long personId)  {
        Meme meme = memeDao.likeMeme(memeId,personId);
        meme.setFavorite(memeDao.setFavourite(memeId, personId));
        return meme;
    }

    @Override
    public Meme unlikeMeme(Long memeId, Long personId)  {
        Meme meme = memeDao.unlikeMeme(memeId,personId);
        meme.setFavorite(memeDao.setFavourite(memeId, personId));
        return meme;
    }



    @Override
    public Meme createMeme(MemeTransporter memeTransporter, Long authorId) {
        List<MemeText> finalList = new ArrayList<>();
        Long imageHeight = memeTransporter.getHeight();
        Long imageWidth = memeTransporter.getWidth();
//        Image originalImage = imageDao.getImageById(memeTransporter.getImageId());
//        InputStream targetStream = new ByteArrayInputStream(originalImage.getFileBlob());
//        BufferedImage realImage = null;
//        try {
//            realImage = ImageIO.read(targetStream);
        for (MemeText memeText : memeTransporter.getTextList()) {
            if (memeText.getMemeText().equals(""))
                continue;
            Long posX = memeText.getxPos() * 100 / imageWidth;
            Long posY = memeText.getyPos() * 100 / imageHeight;
            MemeText newText = new MemeText();
            // need to set memeId somewhere
            newText.setMemeText(memeText.getMemeText());
            newText.setxPos(posX);
            newText.setyPos(posY);
            finalList.add(newText);
        }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return memeDao.createMeme(memeTransporter.getImageId(), memeTransporter.getColor(), memeTransporter.getFontSize(), finalList, authorId);
    }
}
