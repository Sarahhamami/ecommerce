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
    public ResponseEntity<Panier> addPanier(@RequestBody PanierRequest panierRequest) {
        if (panierRequest.getUser_username() == null || panierRequest.getUser_username().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            User user = (User) userDetailsService.loadUserByUsername(panierRequest.getUser_username());

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Panier panier = new Panier();
            panier.setUser(user);
            panier.setDate(panierRequest.getDate());
            panier.setTotalPrice(panierRequest.getTotalPrice());

            panierService.addPanier(panier);

            return new ResponseEntity<>(panier, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public void updatePanier(@PathVariable Long id, @RequestBody PanierRequest panierRequest) {
        User user = (User) userDetailsService.loadUserByUsername(panierRequest.getUser_username());
        Panier panier = new Panier();
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
