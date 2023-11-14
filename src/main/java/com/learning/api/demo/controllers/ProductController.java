package com.learning.api.demo.controllers;

import com.learning.api.demo.models.Product;
import com.learning.api.demo.models.ResponseObject;
import com.learning.api.demo.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    // DI: Dependency Injection
    @Autowired // Đối tượng được tạo ra chạy lần đầu tiên (singleton)
    private ProductRepository productRepository;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable long id){
        Optional<Product> targetProduct = productRepository.findById(id);

        // if(targetProduct.isPresent()) {
        //     return ResponseEntity.status(HttpStatus.OK).body(
        //         new ResponseObject("200", "Get product by id successfully", targetProduct)
        //     );
        // } else {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        //         new ResponseObject("404", "Product not exist", null)
        //     );
        // }

        return targetProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Get product by id successfully", targetProduct)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("404", "Product not exist", null)
            );
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addNewProduct(@RequestBody Product newProduct) {
        // {
        //     "name": "Ipad Pro",
        //     "price": 200
        // }

        List<Product> products = productRepository.findByName(newProduct.getName());

        // Bad request
        if(newProduct.getName() == null || newProduct.getPrice() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("400", "Bad product request", null)
            );
        }

        // Product name is taken
        if(products.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("501", "Product name is already taken", null)
            );
        }            

        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseObject("201", "Add product successfully", productRepository.save(newProduct))
            );
    }

    @PutMapping("/edit")
    public ResponseEntity<ResponseObject> editProductById(@RequestBody Product editProduct) {
        // {
        //     "id": 2,
        //     "name": "Ipad 12 mini",
        //     "price": 200
        // }

         List<Product> products = productRepository.findAll();

        // Bad request
        if(editProduct.getId() == 0 || editProduct.getName() == null || editProduct.getPrice() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("400", "Bad product request", null)
            );
        }

        for (Product product : products) {
            if(product.getId() == editProduct.getId()) {
                product.setName(editProduct.getName());
                product.setPrice(editProduct.getPrice());

                productRepository.save(product);

                return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject("201", "Edit product successfully", editProduct)
                ); 
            }   
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseObject("404", "Product not found", editProduct)
            );      
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
        boolean exist = productRepository.existsById(id);

        if(!exist) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseObject("404", "Product not found", id)
            ); 
        }

        productRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("200", "Delete product successfully", id)
            ); 
    }
}
