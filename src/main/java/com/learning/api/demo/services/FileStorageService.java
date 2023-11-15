package com.learning.api.demo.services;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.learning.api.demo.models.DatabaseFile;
import com.learning.api.demo.repositories.FileRepositoty;

@Service
public class FileStorageService {
    @Autowired
    private FileRepositoty fileDBRepository;

    public DatabaseFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        DatabaseFile FileDB = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public DatabaseFile getFile(String id) {
        return fileDBRepository.findById(id).get();
    }
    
    public Stream<DatabaseFile> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
