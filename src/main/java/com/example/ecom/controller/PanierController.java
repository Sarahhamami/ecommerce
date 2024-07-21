package com.example.ecom.controller;

import com.example.ecom.DTO.PanierRequest;
import com.example.ecom.domain.Panier;
import com.example.ecom.domain.Product;
import com.example.ecom.domain.User;
import com.example.ecom.service.PanierService;
import com.example.ecom.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paniers")
@RequiredArgsConstructor
public class PanierController {
    private final PanierService panierService;
    private final ProductService productService;
    private final UserDetailsService userDetailsService;

    @GetMapping
    public List<Panier> getAllPaniers() {
        return panierService.getAllPanier();
    }

    @GetMapping("/{id}")
    public Panier getPanier(@PathVariable Long id) {
        return panierService.getPanier(id);
    }

    @PostMapping
    public ResponseEntity<String> addPanier(@RequestBody PanierRequest panierRequest) {
        if (panierRequest.getProduct_id()== null) {
            return new ResponseEntity<>("PRODUCT ID must not be null", HttpStatus.BAD_REQUEST);
        }
        if (panierRequest.getUser_username() == null || panierRequest.getUser_username().isEmpty()){
            return new ResponseEntity<>("User ID must not be empty or null", HttpStatus.BAD_REQUEST);
        }



        try {
            Product product = productService.getProduct(panierRequest.getProduct_id());

            if (product == null) {
                return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
            }
            User user = (User) userDetailsService.loadUserByUsername(panierRequest.getUser_username());

            if (user == null) {
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }
            Panier panier = new Panier();
            panier.setProduct(product);
            panier.setUser(user);
            panier.setDate(panierRequest.getDate());
            panier.setTotalPrice(panierRequest.getTotalPrice());


            panierService.addPanier(panier);

            return new ResponseEntity<>("Panier added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public void updatePanier(@PathVariable Long id, @RequestBody PanierRequest panierRequest) {
        Product product = productService.getProduct(panierRequest.getProduct_id());
        User user = (User) userDetailsService.loadUserByUsername(panierRequest.getUser_username());
        Panier panier = new Panier();
        panier.setProduct(product);
        panier.setUser(user);
        panier.setDate(panierRequest.getDate());
        panier.setTotalPrice(panierRequest.getTotalPrice());
        panierService.updatePanier(id, panier);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        panierService.deletePanier(id);
    }

}
