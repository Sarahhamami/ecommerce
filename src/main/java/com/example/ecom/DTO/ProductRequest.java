package com.example.ecom.DTO;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductRequest {

        private String name;
        private String description;
        private double price;
        private String ref;
        private Long categoryId;
    }


