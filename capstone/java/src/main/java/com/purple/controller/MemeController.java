package com.purple.controller;

import com.purple.dao.TagDao;
import com.purple.handler.ImageHandlerable;
import com.purple.handler.MemeHandlerable;
import com.purple.model.*;
import com.purple.security.Authorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MemeController extends BaseController{

    private final ImageHandlerable imageHandler;
    private final MemeHandlerable memeHandler;
    private final TagDao tagDao;

    @Autowired
    public MemeController(ImageHandlerable ImageHandler, MemeHandlerable memeHandler, TagDao tagDao){
        this.imageHandler = ImageHandler;
        this.memeHandler = memeHandler;
        this.tagDao = tagDao;
    }

    //view top 5 memes (home page)
    @GetMapping(value = "/meme/top6", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopMemes(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.getTopMemes(6)); //get top 5
    }

    //view favourite memes (by user)
    @GetMapping(value = "/meme/favourites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterMemesByFavourites(HttpServletRequest request, HttpServletResponse response, Long id) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.filterMemesByFavourites(id));
    }

    //view all memes - maybe we can add filtering in here and I can call different methods inside?
    @GetMapping(value = "/meme/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMemes(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.getAllMemes());
    }

    //view all memes - maybe we can add filtering in here and I can call different methods inside?
    @GetMapping(value = "/meme/all/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPopularMemes(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(memeHandler.getPopularMemes());
    }

    //view all memes - maybe we can add filtering in here and I can call different methods inside?
    @GetMapping(value = "/meme/all/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecentMemes(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(memeHandler.getNewestMemes());
    }

    //view all memes - maybe we can add filtering in here and I can call different methods inside?
    @GetMapping(value = "/meme/unapproved", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUnapprovedMemes(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(memeHandler.getAllUnapprovedMemes());
    }

// search by word inside the meme text OR matches a tag on an image
    @GetMapping(value = "/memeSearch/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMeme(HttpServletRequest request, HttpServletResponse response,@PathVariable String search) {
        if (search == null) {
            return getAllMemes(request, response);
        }
        return ResponseEntity.ok(this.memeHandler.searchMemeByWordOrTag(search));
    }


    //the meme image
    @GetMapping(value="/meme/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMemeImage(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User user = super.getCurrentUserFromCookie(request);
        Long userId = (user == null ? 0L : user.getUserId());
        Meme meme = memeHandler.getMemeById(id, userId);
        Image image = imageHandler.getWebImageWithMeme(meme);
        if (image==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=a.png")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(image.getFileBlob());
    }

    @PostMapping(value = "/meme/report/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meme> reportMeme(@PathVariable Long id) {
        return ResponseEntity.ok(memeHandler.reportMeme(id));
    }

    @PostMapping(value = "/meme/delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meme> deleteMeme(@PathVariable Long id) {
        return ResponseEntity.ok(memeHandler.deleteMeme(id));
    }

    @PostMapping(value = "/meme/approve/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meme> approveMeme(@PathVariable Long id) {
        return ResponseEntity.ok(memeHandler.approveMeme(id));
    }

    @GetMapping(value = "/meme/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMemeDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User user = super.getCurrentUserFromCookie(request);
        Long userId = (user == null ? 0L : user.getUserId());
        return ResponseEntity.ok(this.memeHandler.getMemeById(id, userId));
    }

    @PostMapping(value = "/meme/like/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<Meme> likeMeme(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(memeHandler.likeMeme(id, user.getUserId()));
    }

    @PostMapping(value = "/meme/unlike/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<Meme> unlikeMeme(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(memeHandler.unlikeMeme(id, user.getUserId()));
    }

    //create a meme
    @PostMapping(value = "createMeme", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<?> createMeme(HttpServletRequest request, HttpServletResponse response, @RequestBody MemeTransporter memeTransporter) {
        if (memeTransporter == null) {
            return ResponseEntity.badRequest().body("Meme not created.");
        }
        User user = super.getCurrentUserFromAttribute(request);

        return ResponseEntity.ok(memeHandler.createMeme(memeTransporter, user.getUserId()));
    }


    //view all images - templateMeme - maybe we can add filtering in here and I can call different methods inside?
    @GetMapping(value = "/image/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<?> getAllImages(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(this.imageHandler.getAllImages());
    }

    @GetMapping(value = "/image/all/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPopularImages(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.getPopularMemes());
    }

    @GetMapping(value = "/image/all/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecentImages(HttpServletRequest request, HttpServletResponse response) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.memeHandler.getNewestMemes());
    }

    // search images by tag

    @GetMapping(value = "/imageSearch/{tagName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchImage(HttpServletRequest request, HttpServletResponse response,@PathVariable String tagName) {
        if (tagName == null) {
            return getAllImages(request, response);

        }
        return ResponseEntity.ok(this.imageHandler.filterImagesByTag(tagName));

    }

    @GetMapping(value = "/image/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getImage(HttpServletRequest request, HttpServletResponse response,@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=a.png")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(this.imageHandler.getImage(id).getFileBlob());
    }

    @PostMapping(value = "/image/delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Image> deleteImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageHandler.deleteImage(id));
    }



    @PostMapping(value = "/image/createTag",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<Tag> createTag(@RequestBody TagTransporter tagTransporter) {
        return ResponseEntity.ok(imageHandler.createTag(tagTransporter.getTagName(), tagTransporter.getImageId()));
    }

    @PostMapping(value = "/image/assignTag",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Authorized
    public ResponseEntity<Image> assignTag(@RequestBody TagTransporter tagTransporter) {
        return ResponseEntity.ok(imageHandler.assignTagToImage(tagTransporter.getImageId(), tagTransporter.getTagId()));
    }

    @Authorized
    public ResponseEntity<Image> removeTag(@RequestBody Long tagId, @RequestBody Long imageId) {
        return ResponseEntity.ok(imageHandler.removeTagFromImage(imageId, tagId));
    }

    @GetMapping(value = "/image/allTags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTags(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.imageHandler.getAllTagsNotImageId(id));
    }

    @GetMapping(value = "/image/tags/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTagsByImageId(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
//        User user = super.getCurrentUserFromAttribute(request);
        return ResponseEntity.ok(this.imageHandler.getTagsByImageId(id));
    }

}
