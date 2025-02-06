package com.example.imageStore.Controllers;

import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.imageStore.Service.ImageFileService;


@RestController
@RequestMapping(value = "/api/v1/images")
public class ImagesController {
    @Autowired
    private ImageFileService imageStoreService;
    @PostMapping("/{name}")
    public ResponseEntity<?> postImage(@RequestParam("upload") MultipartFile file, @PathVariable String name) {
        String fileName = file.getOriginalFilename();
        String fileExtention = FilenameUtils.getExtension(fileName);
        fileName = name + "." + fileExtention;
        try {
            InputStream fileInputStream = file.getInputStream();
            imageStoreService.addFileToStore(fileName ,fileInputStream);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestParam String fullName) {
        try {
            imageStoreService.removeFileInStore(fullName);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
