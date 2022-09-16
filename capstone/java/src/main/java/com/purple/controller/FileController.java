
package com.purple.controller;

import com.purple.handler.ImageHandlerable;
import com.purple.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FileController extends BaseController {

    private final ImageHandlerable imageHandler;

    @Autowired
    public FileController(ImageHandlerable imageHandler){
        this.imageHandler = imageHandler;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadFile(@RequestParam MultipartFile file) {

        Image image = new Image();
        image.setFileSize(file.getSize());
        try {
            image.setFileBlob(file.getBytes());
            return ResponseEntity.ok(imageHandler.uploadImage(image));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("/file/{id}")
    public ResponseEntity<?> getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        Image image = this.imageHandler.getImage(id);
        if (image==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=a.png")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(image.getFileBlob());
    }
}
