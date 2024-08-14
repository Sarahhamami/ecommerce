package com.example.ecom.controller;

import com.example.ecom.DTO.ProductRequest;
import com.example.ecom.domain.Category;
import com.example.ecom.domain.Product;
import com.example.ecom.domain.Topic;
import com.example.ecom.service.CategoryService;
import com.example.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        if (productRequest.getCategoryId() == null) {
            return new ResponseEntity<>("Category ID must not be null", HttpStatus.BAD_REQUEST);
        }

        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            return new ResponseEntity<>("Product name must not be null or empty", HttpStatus.BAD_REQUEST);
        }

        try {
            Category category = categoryService.getCategory(productRequest.getCategoryId());

            if (category == null) {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }

            Product product = new Product();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setRef(productRequest.getRef());
            product.setCategory(category);

            productService.addProduct(product);

            return new ResponseEntity<>("Product added succeffully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Category category = categoryService.getCategory(productRequest.getCategoryId());
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setRef(productRequest.getRef());
        product.setCategory(category);
        productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
