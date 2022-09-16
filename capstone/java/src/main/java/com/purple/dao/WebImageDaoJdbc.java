package com.purple.dao;

import com.purple.model.WebImage;
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
public class WebImageDaoJdbc implements WebImageDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public WebImageDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public WebImage getWebImage(Long id){
        String sql = "select i.image_id, i.file_name, i.mime_type, i.file_blob, i.file_size from image i where i.image_id = :id;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToWebImage(results);
        }
        return null;
    }

    @Override
    public List<WebImage> getWebImages(){
        String sql = """
                select
                    i.image_id,
                    i.file_name,
                    i.mime_type,
                    i.file_blob,
                    i.file_size
                from
                    image i
                where
                    active = true;
                """;
        Map<String,Object> params = new HashMap<>();

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        return mapRowsToWebImages(results);
    }
    @Override
    public WebImage saveWebImage(WebImage webImage){
        String sql = """
                insert into image (file_name, mime_type, file_size, file_blob, active)
                    values
                    (:file_name, :mime_type, :file_size, :file_blob, true)
                    returning image_id;
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("file_name",webImage.getFileName());
        params.put("mime_type",webImage.getMimeType());
        params.put("file_size",webImage.getFileSize());
        params.put("file_blob",webImage.getImage());
        Long id = jdbcTemplate.queryForObject(sql, params, Long.class);
        return getWebImage(id);
    }
    @Override
    public void deleteWebImage(Long id){
        String sql = """
                update image
                    set
                        active = false
                where
                    image_id = :id;
                """;
        Map<String,Object> params = new HashMap<>();
        jdbcTemplate.update(sql, params);
    }
    private List<WebImage> mapRowsToWebImages(SqlRowSet results){
        List<WebImage> webImages = new ArrayList<>();
        while (results.next()){
            webImages.add(mapRowToWebImage(results));
        }
        return webImages;
    }
    private WebImage mapRowToWebImage(SqlRowSet results) {
        WebImage webImage = new WebImage();
        webImage.setId(results.getLong("image_id"));
        webImage.setFileName(results.getString("file_name"));
        webImage.setMimeType(results.getString("mime_type"));
        webImage.setImage((byte[])results.getObject("file_blob"));

        return webImage;
    }
}
