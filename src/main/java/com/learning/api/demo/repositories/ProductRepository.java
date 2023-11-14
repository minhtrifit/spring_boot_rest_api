package com.learning.api.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.api.demo.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
}
