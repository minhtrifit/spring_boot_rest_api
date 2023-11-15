package com.learning.api.demo.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.learning.api.demo.services.FilesStorageService;

public class DatabaseLocal implements CommandLineRunner {
    @Autowired
    FilesStorageService filesStorageService;

    @Override
    public void run(String... arg) throws Exception {
        filesStorageService.deleteAll();
        filesStorageService.init();
      }
}
