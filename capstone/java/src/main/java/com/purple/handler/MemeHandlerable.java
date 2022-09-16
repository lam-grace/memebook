package com.purple.handler;

import com.purple.model.Image;
import com.purple.model.Meme;
import com.purple.model.MemeTransporter;
import com.purple.model.User;

import java.util.List;

public interface MemeHandlerable {
    public Meme getMemeById(Long id, Long currentUserId);
    public List<Meme> getAllMemes();
    public List<Meme> getAllUnapprovedMemes();
    public List<Meme> getTopMemes(Integer number);
    public List<Meme> filterMemesBySourceImageId(User author);
    public List<Meme> filterMemesByAuthor(Long userId);
    public List<Meme> filterMemesByWord(String word);
    public List<Meme> searchMemeByWordOrTag(String word);
    public List<Meme> filterMemesByTag(String tag);
    public List<Meme> getNewestMemes();
    public List<Meme> getPopularMemes();
    public List<Meme> filterMemesByFavourites(Long userId);
    public Meme reportMeme(Long id);
    public Meme deleteMeme(Long id);
    public Meme approveMeme(Long id);
    public Meme likeMeme(Long memeId, Long personId);
    public Meme unlikeMeme(Long memeId, Long personId);
    public Meme createMeme(MemeTransporter memeTransporter, Long authorId);
}
