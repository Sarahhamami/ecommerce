package com.example.ecom.service;

import com.example.ecom.domain.Category;
import com.example.ecom.domain.Topic;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategory(Long id);
    void addCategory(Category category);
    void updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
