package com.learning.api.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.api.demo.models.DatabaseFile;

@Repository
public interface FileRepositoty extends JpaRepository<DatabaseFile, String> {
    
}
