package com.purple.dao;

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
public class MemeTextDaoJdbc implements MemeTextDao {
    public final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public MemeTextDaoJdbc(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    @Override
    public MemeText getMemeText(Long id) {
        String sql = "select mt.* from meme_text mt where mt.text_id = :id;";
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToMemeText(results);
        }
        return null;
    }

    @Override
    public List<MemeText> getAllTextsByMemeId(Long memeId) {
        List<MemeText> memeTexts = new ArrayList<>();
        String sql = """
                select mt.* 
                from meme_text mt
                where mt.meme_id = :memeId;""";
        Map<String,Object> params = new HashMap<>();
        params.put("memeId", memeId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()) {
            memeTexts.add(mapRowToMemeText(results));
        }
        return memeTexts;
    }


    @Override
    public MemeText mapRowToMemeText(SqlRowSet results) {
        MemeText memeText = new MemeText();
        memeText.setTextId(results.getLong("text_id"));
        memeText.setMemeText(results.getString("text"));
        memeText.setMemeId(results.getLong("meme_id"));
        memeText.setxPos(results.getLong("location_x"));
        memeText.setyPos(results.getLong("location_y"));

        return memeText;
    }

}
