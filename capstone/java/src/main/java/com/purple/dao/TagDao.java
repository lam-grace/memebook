package com.purple.dao;


import com.purple.model.Tag;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TagDao {
    public List<Tag> getAllTags();
    public List<Tag> getAllTagsByImageId(Long imageId);
    public List<Tag> getAllTagsNotImageId(Long imageId);
    public Tag getTagByName(String tag);
    public Tag deleteTag(Long tagId);
    public Tag createTag(String tagName);
    public Tag mapRowToTag(SqlRowSet results);
    public void assignTagToImage(Long imageId, Long tagId);
    public void removeTagFromImage(Long imageId, Long tagId);
}

