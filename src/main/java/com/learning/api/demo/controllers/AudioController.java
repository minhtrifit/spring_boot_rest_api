package com.learning.api.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.learning.api.demo.models.FileInfo;
import com.learning.api.demo.services.AudioService;

@Controller
@RequestMapping(path = "/audio")
public class AudioController {
    @Autowired
    AudioService audioService;

    @GetMapping("/all")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = audioService.loadAll().map(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
            .fromMethodName(AudioController.class, "getAudio", path.getFileName().toString()).build().toString();

        return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<Resource> getAudio(@PathVariable String filename) {
        Resource file = audioService.load(filename);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
