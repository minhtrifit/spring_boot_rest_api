package com.learning.api.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.learning.api.demo.models.FileInfo;
import com.learning.api.demo.models.ResponseMessage;
import com.learning.api.demo.services.FilesStorageService;

@Controller
@RequestMapping(path = "/files")
public class FilesController {
    @Autowired
    FilesStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        // const onSubmit = async (data: any) => {
        //     const formData = new FormData();
        
        //     for (let i = 0; i < data.file.length; ++i) {
        //         formData.append("files", data.file[i]);
        //     }
        
        //     const res = await fetch("http://localhost:8080/files/upload", {
        //         method: "POST",
        //         body: formData,
        //     }).then((res) => res.json());
        // };

        String message = "";
        try {
        List<String> fileNames = new ArrayList<>();

        Arrays.asList(files).stream().forEach(file -> {
            storageService.save(file);
            fileNames.add(file.getOriginalFilename());
        });

        message = "Uploaded the files successfully: " + fileNames;
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (Exception e) {
        message = "Fail to upload files!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
            .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

        return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
