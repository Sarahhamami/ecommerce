package com.example.ecom.service;

import com.example.ecom.domain.Product;
import com.example.ecom.domain.Topic;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    void addProduct(Product product);
    void updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
