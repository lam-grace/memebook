package com.purple.dao;

import com.purple.model.Meme;
import com.purple.model.MemeText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class MemeDaoJdbc implements MemeDao {
    public final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public MemeDaoJdbc(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    @Override
    public Meme getMemeById(Long id){
        String sql = "select m.*, p.username, p.person_id from meme m" +
                " join meme_author ma on m.meme_id = ma.meme_id" +
                " join person p on ma.author_id = p.person_id" +
                " where m.meme_id = :id and m.active is true;";
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToMeme(results, true);
        }
        return null;
    }

    @Override
    public List<Meme> getAllMemes() {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> getAllUnapprovedMemes() {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is false;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> getTopMemes(Integer number) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    order by m.popularity desc limit :number;""";
        Map<String,Object> params = new HashMap<>();
        params.put("number", number);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> filterMemesByTag(String tag) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    join image i on i.image_id = m.image_id
                    join image_tag it on i.image_id = it.image_id
                    join tag t on t.tag_id = it.tag_id
                    where m.active is true and m.approved is true 
                    and t.tag_name ilike :tag;""";
        Map<String,Object> params = new HashMap<>();
        params.put("tag", "%" + tag + "%");
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> searchMemeByWordOrTag(String word) {
        List<Meme> memes = new ArrayList<>();
        if (word.equals(""))
            return getAllMemes();
        String sql = """
                select distinct m.*
                from meme m
                     join image i on i.image_id = m.image_id
                     join image_tag it on i.image_id = it.image_id
                     join tag t on t.tag_id = it.tag_id
                     join meme_text mt on m.meme_id = mt.meme_id
                where m.active is true and m.approved is true
                          and (t.tag_name ilike :word or mt.text ilike :word);""";
        Map<String,Object> params = new HashMap<>();
        params.put("word", "%" + word + "%");
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, false));
        }
        return memes;
    }

    @Override
    public List<Meme> filterMemesByAuthor(Long authorId) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    and author_id = :authorId;""";
        Map<String,Object> params = new HashMap<>();
        params.put("authorId", authorId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> filterMemesByFavourites(Long personId) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                from                   
                    favourite_meme fm
                    join meme m on m.meme_id = fm.meme_id
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    and fm.person_id=:personId;""";
        Map<String,Object> params = new HashMap<>();
        params.put("personId", personId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> filterMemesBySourceImageId(Long imageId) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                  from
                  meme m
                  join meme_author ma on m.meme_id = ma.meme_id
                  join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    and m.image_id = :imageId;""";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId", imageId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> filterMemesByWord(String word) {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select distinct
                    m.*, p.username, p.person_id 
                      from
                      meme m
                      join meme_author ma on m.meme_id = ma.meme_id
                      join person p on ma.author_id = p.person_id
                    join meme_text mt on m.meme_id = mt.meme_id
                    where m.active is true and m.approved is true
                    and mt.text ilike :word;""";
        Map<String,Object> params = new HashMap<>();
        params.put("word", "%" + word + "%");
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> getNewestMemes() {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    order by m.date_uploaded desc;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    @Override
    public List<Meme> getPopularMemes() {
        List<Meme> memes = new ArrayList<>();
        String sql = """
                select m.*, p.username, p.person_id 
                    from
                    meme m
                    join meme_author ma on m.meme_id = ma.meme_id
                    join person p on ma.author_id = p.person_id
                    where m.active is true and m.approved is true
                    order by m.popularity desc;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            memes.add(mapRowToMeme(results, true));
        }
        return memes;
    }

    //Like a meme - update meme popularity and favourite_meme table
    @Override
    public Meme likeMeme(Long memeId, Long personId){
        String sql = "update meme " +
                "set popularity = popularity + 1 " +
                "where meme_id = :memeId; " +
                "insert into favourite_meme " +
                "(person_id, meme_id) " +
                "values " +
                "(:personId, :memeId);";
        Map<String,Object> params = new HashMap<>();
        params.put("memeId",memeId);
        params.put("personId",personId);
        jdbcTemplate.update(sql, params);
        return getMemeById(memeId);
    }

    //Unlike a liked meme
    @Override
    public Meme unlikeMeme(Long memeId, Long personId){
        String sql = "update meme " +
                "set popularity = popularity - 1 " +
                "where meme_id = :memeId; " +
                "delete from favourite_meme " +
                "where " +
                "person_id = :personId and  meme_id= :memeId ";
        Map<String,Object> params = new HashMap<>();
        params.put("memeId",memeId);
        params.put("personId",personId);
        jdbcTemplate.update(sql, params);
        return getMemeById(memeId);
    }

    @Override
    public Meme reportMeme(Long id) {
        String sql = "update meme " +
                " set approved=false " +
                " where meme_id = :id" +
                " returning *;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);

        if (results.next()) {
            return mapRowToMeme(results, false);
        }
        return null;
    }

    @Override
    public Meme deleteMeme(Long id) {
        String sql = "update meme " +
                " set active=false " +
                " where meme_id = :id" +
                " returning *;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);

        if (results.next()) {
            return mapRowToMeme(results, false);
        }
        return null;
    }

    @Override
    public void deleteMemesByImageId(Long imageId) {
        String sql = "update meme " +
                " set active=false " +
                " where image_id = :imageId";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId", imageId);
        jdbcTemplate.update(sql,params);
    }

    @Override
    public Meme approveMeme(Long id) {
        String sql = "update meme " +
                " set approved=true " +
                " where meme_id = :id" +
                " returning *;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);

        if (results.next()) {
            return mapRowToMeme(results, false);
        }
        return null;
    }

    public Meme createMeme(Long imageId, String color, Integer fontSize, List<MemeText> textLists, Long authorId){
        Meme meme = new Meme();
        String sql ="""
                INSERT INTO meme (image_id, font_color, font_size) VALUES (:imageId, :color, :fontSize) returning *;
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("imageId", imageId);
        params.put("color", color);
        params.put("fontSize", fontSize);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            meme = mapRowToMeme(results, false);
        }
        sql ="""
                INSERT INTO meme_author (author_id, meme_id) VALUES (:authorId, :memeId);
                """;
        params.put("authorId", authorId);
        params.put("memeId", meme.getMemeId());
        jdbcTemplate.update(sql,params);

        for (MemeText text : textLists) {
            sql ="""
                INSERT INTO meme_text(meme_id, text, location_x, location_y) VALUES (:memeId, :text, :location_x, :location_y);
                """;
            params.put("text", text.getMemeText());
            params.put("location_x", text.getxPos());
            params.put("location_y", text.getyPos());
            jdbcTemplate.update(sql,params);
        }
        return meme;
    }

    @Override
    public Boolean setFavourite(Long memeId, Long currentUserId) {
        String sql = """
                select m.*
                    from
                    favourite_meme fm
                    left join meme m on m.meme_id = fm.meme_id
                    where fm.person_id = :currentUserId
                    and m.meme_id = :memeId 
                    and m.active is true and m.approved is true;""";
        Map<String,Object> params = new HashMap<>();
        params.put("memeId", memeId);
        params.put("currentUserId", currentUserId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return true;
        }
        return false;

    }

    @Override
    public Meme mapRowToMeme(SqlRowSet results, Boolean getUser) {
        Meme meme = new Meme();
        meme.setMemeId(results.getLong("meme_id"));
        meme.setImageId(results.getLong("image_id"));

        if (results.getDate("date_uploaded")!=null) {
            meme.setUploadDate(results.getDate("date_uploaded").toLocalDate());
        }

        meme.setPopularity(results.getLong("popularity"));
        meme.setActive(results.getBoolean("active"));
        meme.setApproved(results.getBoolean("approved"));
        if (getUser) {
            meme.setAuthorId(results.getLong("person_id"));
            meme.setAuthor(results.getString("username"));
            meme.setColor(results.getString("font_color"));
            meme.setFontSize(results.getInt("font_size"));
        }
        return meme;
    }

}
