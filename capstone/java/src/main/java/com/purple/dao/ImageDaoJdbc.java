package com.purple.dao;

import com.purple.model.Image;
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
public class ImageDaoJdbc implements ImageDao{
    public final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ImageDaoJdbc(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    @Override
    public Image getImageById(Long id){
        String sql = "select i.* from image i where i.image_id = :id;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToImage(results);
        }
        return null;
    }

    //get the template image used by a meme
    @Override
    public Image getImageByMemeId(Long memeId){
        String sql = "select i.* from image i " +
                "join meme m on i.image_id = m.image_id " +
                "where m.meme_id = :memeId " +
                "and m.active is true and i.active is true;";
        Map<String,Object> params = new HashMap<>();
        params.put("memeId", memeId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToImage(results);
        }
        return null;
    }


    // Save a new uploaded image to the database
    @Override
    public Image uploadImage(Image image){
        String sql = """
                insert into image (file_size, file_blob)
                    values
                    (:file_size, :file_blob)
                    returning image_id;
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("file_size",image.getFileSize());
        params.put("file_blob",image.getFileBlob());
        params.put("date_uploaded", image.getDateUploaded());
        params.put("active", image.isActive());
        Long id = jdbcTemplate.queryForObject(sql, params, Long.class);

        return getImageById(image.getImageId());
    }

    @Override
    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    where active is true;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }

    //filter images by tag
    @Override
    public List<Image> filterImagesByTag(String tagName) {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    join image_tag it on i.image_id = it.image_id
                    join tag t on t.tag_id = it.tag_id
                    where i.active is true and t.active is true and tag_name ilike :tagName;""";
        Map<String,Object> params = new HashMap<>();
        params.put("tagName", tagName);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }

    //filter by author
    @Override
    public List<Image> filterImagesByAuthor(Long authorId) {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    join image_author ia on i.image_id=ia.image_id
                    where active is true and author_id=:authorId;""";
        Map<String,Object> params = new HashMap<>();
        params.put("authorId", authorId);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }


    //sort by popularity
    @Override
    public List<Image> getPopularImages() {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    where active is true
                    order by i.popularity desc;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }

    //sort by date
    @Override
    public List<Image> getNewestImages() {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    where active is true
                    order by i.date_uploaded desc;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }

    @Override
    public Image deleteImage(Long id) {
        String sql = "update image " +
                " set active=false " +
                " where image_id = :id " +
                " returning *;";
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,params);

        if (results.next()) {
            return mapRowToImage(results);
        }
        return null;
    }

    //Like an image - update image popularity and favourite_image table
    @Override
    public void likeImage(Long imageId, Long personId){
        String sql = "update image " +
                "set popularity = popularity + 1 " +
                "where image_id = :imageId; " +
                "insert into favourite_image " +
                "(person_id, image_id) " +
                "values " +
                "(:personId, :imageId);";
        Map<String,Object> params = new HashMap<>();
        params.put("image_id",imageId);
        params.put("person_id",personId);
        jdbcTemplate.update(sql,params);
    }

    //List all images liked by user
    @Override
    public List<Image> getAllLikedImagesByUser(Long personID) {
        List<Image> images = new ArrayList<>();
        String sql = """
                select
                        i.*
                    from
                    image i
                    join favourite_image fi on i.image_id = fi.image_id
                    where fi.person_id = :personID;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        while (results.next()){
            images.add(mapRowToImage(results));
        }
        return images;
    }

    @Override
    public Image mapRowToImage(SqlRowSet results) {
        Image image = new Image();
        image.setImageId(results.getLong("image_id"));
        image.setFileSize(results.getLong("file_size"));
        image.setFileBlob((byte[])results.getObject("file_blob"));
        if (results.getDate("date_uploaded")!=null) {
            image.setDateUploaded(results.getDate("date_uploaded").toLocalDate());
        }
        image.setPopularity(results.getLong("popularity"));
        image.setActive(results.getBoolean("active"));


        return image;
    }


}
