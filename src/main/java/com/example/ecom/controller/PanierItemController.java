package com.example.ecom.controller;

import com.example.ecom.DTO.PanierItemRequest;
import com.example.ecom.DTO.PanierRequest;
import com.example.ecom.domain.Panier;
import com.example.ecom.domain.PanierItem;
import com.example.ecom.domain.Product;
import com.example.ecom.domain.User;
import com.example.ecom.service.PanierItemService;
import com.example.ecom.service.PanierService;
import com.example.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/panierItems")
@RequiredArgsConstructor
public class PanierItemController {
    private final PanierItemService panierItemService;
    private final ProductService productService;
    private final PanierService panierService;


    @GetMapping
    public List<PanierItem> getAllPanierItems() {
        return panierItemService.getAllPanierItem();
    }

    @GetMapping("/{id}")
    public PanierItem getPanierItem(@PathVariable Long id) {
        return panierItemService.getPanierItem(id);
    }

    @PostMapping
    public ResponseEntity<String> addPanierItem(@RequestBody PanierItemRequest panierItemRequest) {

        if (panierItemRequest.getPanier_id() == null ){
            return new ResponseEntity<>("panier ID must not be null", HttpStatus.BAD_REQUEST);
        }

        if (panierItemRequest.getProduct_id() == null ){
            return new ResponseEntity<>("product ID must not be null", HttpStatus.BAD_REQUEST);
        }

        try {

            Product product =  productService.getProduct(panierItemRequest.getProduct_id());

            if (product == null) {
                return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
            }
            Panier panier =  panierService.getPanier(panierItemRequest.getPanier_id());

            if (product == null) {
                return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
            }
            PanierItem panierItem = new PanierItem();

            panierItem.setProductId(product);
            panierItem.setQuantity(panierItemRequest.getQuantity());
            panierItem.setPanier(panier);


            panierItemService.addPanierItem(panierItem);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public void updatePanierItem(@PathVariable Long id, @RequestBody PanierItemRequest panierItemRequest) {
        Product product = productService.getProduct(panierItemRequest.getProduct_id());
        Panier panier = panierService.getPanier(panierItemRequest.getPanier_id());
        PanierItem panierItem = new PanierItem();
        panierItem.setPanier(panier);
        panierItem.setQuantity(panierItemRequest.getQuantity());
        panierItem.setProductId(product);

        panierItemService.updatePanierItem(id, panierItem);
    }

    @DeleteMapping("/{id}")
    public void deletePanierItem(@PathVariable Long id) {
        panierItemService.deletePanierItem(id);
    }

}
