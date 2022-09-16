package com.purple.dao;

import com.purple.model.MemeText;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface MemeTextDao {
    public MemeText getMemeText(Long id);
    public List<MemeText> getAllTextsByMemeId(Long memeId);

    MemeText mapRowToMemeText(SqlRowSet results);
}

