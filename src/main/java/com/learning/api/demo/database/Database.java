package com.learning.api.demo.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learning.api.demo.repositories.ProductRepository;

@Configuration // Chứa các Bean method
public class Database {
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Product productA = new Product("Iphone 15 Promax", 200);
                // Product productB = new Product("Laptop Asus", 50);

                // productRepository.save(productA);
                // productRepository.save(productB);
            }
        };
    }
}
