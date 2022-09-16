package com.purple.dao;

import com.purple.model.MemeText;
import com.purple.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagDaoJdbc implements TagDao{
    public final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoJdbc(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = """
                select t.*
                from tag t
                where t.active is true;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()) {
            tags.add(mapRowToTag(results));
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTagsByImageId(Long imageId) {
        List<Tag> tags = new ArrayList<>();
        String sql = """
                select t.*
                from tag t
                join image_tag it on t.tag_id = it.tag_id
                where it.image_id = :imageId
                and t.active is true;""";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId", imageId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()) {
            tags.add(mapRowToTag(results));
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTagsNotImageId(Long imageId) {
        List<Tag> tags = new ArrayList<>();
        String sql = """
                select distinct t.*
                from tag t
                         left join image_tag it on t.tag_id = it.tag_id
                        and it.image_id = :imageId
                where it.image_id is null;""";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId", imageId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()) {
            tags.add(mapRowToTag(results));
        }
        return tags;
    }

    @Override
    public Tag getTagByName(String tag) {
        String sql = "select * from tag where tag_name like :tag;";
        Map<String,Object> params = new HashMap<>();
        params.put("tag","%" + tag + "%");
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToTag(results);
        }
        return null;
    }

    @Override
    public Tag deleteTag(Long tagId) {
        String sql = "update tag " +
                " set active=false " +
                " where tag_id = :tagId";
        Map<String,Object> params = new HashMap<>();
        params.put("tagId",tagId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);
        if (results.next()) {
            return mapRowToTag(results);
        }
        return null;
    }

    @Override
    public Tag createTag(String tagName) {
        String sql = "insert into tag (tag_name, active)" +
                " values (:tagName, true)" +
                " returning *";
        Map<String,Object> params = new HashMap<>();
        params.put("tagName",tagName);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);
        if (results.next()) {
            return mapRowToTag(results);
        }
        return null;
    }

    @Override
    public void assignTagToImage(Long imageId, Long tagId) {
        String sql = "insert into image_tag (image_id, tag_id)" +
                " values (:imageId, :tagId);";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId",imageId);
        params.put("tagId",tagId);
        jdbcTemplate.update(sql,params);
    }

    @Override
    public void removeTagFromImage(Long imageId, Long tagId) {
        String sql = "delete from image_tag  " +
                "where (image_id = :imageId, tag_id = :tag_id);";
        Map<String,Object> params = new HashMap<>();
        params.put("imageId",imageId);
        params.put("tagId",tagId);
        jdbcTemplate.update(sql,params);
    }

    @Override
    public Tag mapRowToTag(SqlRowSet results) {
        Tag tag = new Tag();
        tag.setTagId(results.getLong("tag_id"));
        tag.setTagName(results.getString("tag_name"));
        tag.setActive(results.getBoolean("active"));
        return tag;
    }
}
