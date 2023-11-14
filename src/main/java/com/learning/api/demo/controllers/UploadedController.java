package com.learning.api.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.learning.api.demo.models.ResponseObject;
import com.learning.api.demo.services.UploadImageService;

@Controller
@RequestMapping("/upload")
public class UploadedController {
    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            //save files to a folder => use a service
            String generatedFileName = uploadImageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
               new ResponseObject("ok", "upload file successfully", generatedFileName)
            );
            //06a290064eb94a02a58bfeef36002483.png => how to open this file in Web Browser ?
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
               new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

    @GetMapping("/files/{fileName:.+}")
     // /files/06a290064eb94a02a58bfeef36002483.png
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = uploadImageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }
        catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }
}
