package com.example.ecom.DTO;

import com.example.ecom.domain.Panier;
import com.example.ecom.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanierItemRequest {

    private Long id;
    private Long panier_id;
    private Long product_id;
    private int quantity;
}
