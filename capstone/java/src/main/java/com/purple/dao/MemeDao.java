package com.purple.dao;

import com.purple.model.Meme;
import com.purple.model.MemeText;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface MemeDao {
    Meme getMemeById(Long id);
    List<Meme> getAllMemes();
    public List<Meme> getAllUnapprovedMemes();
    public List<Meme> getTopMemes(Integer number);
    public List<Meme> filterMemesBySourceImageId(Long authorId);
    public List<Meme> filterMemesByAuthor(Long authorId);
    public List<Meme> filterMemesByWord(String word);
    public List<Meme> filterMemesByTag(String tag);
    public List<Meme> getNewestMemes();
    public List<Meme> getPopularMemes();
    public List<Meme> filterMemesByFavourites(Long personId);
    public List<Meme> searchMemeByWordOrTag(String word);
    public Meme likeMeme(Long memeId, Long personId);
    public Meme unlikeMeme(Long memeId, Long personId);
    public Meme reportMeme(Long id);
    public Meme deleteMeme(Long id);
    public void deleteMemesByImageId(Long imageId);
    public Meme approveMeme(Long id);
    public Meme createMeme(Long imageId, String color, Integer fontSize, List<MemeText> textLists, Long authorId);
    public Boolean setFavourite(Long memeId, Long currentUserId);
    Meme mapRowToMeme(SqlRowSet results, Boolean getUser);
}
