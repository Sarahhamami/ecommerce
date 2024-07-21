package com.example.ecom.service;

import com.example.ecom.CustomException.ResourceNotFoundException;
import com.example.ecom.domain.Panier;
import com.example.ecom.domain.Product;
import com.example.ecom.repository.PanierRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PanierServiceImpl implements PanierService{
    private final PanierRepository panierRepository;
    @Override
    public List<Panier> getAllPanier() {
        return panierRepository.findAll();
    }

    @Override
    public Panier getPanier(Long id) {
        return panierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Panier not found with id " + id));
    }

    @Override
    public void addPanier(Panier panier) {
        panierRepository.save(panier);
    }

    @Override
    public void updatePanier(Long id, Panier panier) {
        Panier existingPanier= panierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Panier not found with id " + id));
        existingPanier.setDate(panier.getDate());
        existingPanier.setUser(panier.getUser());
        existingPanier.setProduct(panier.getProduct());
        existingPanier.setTotalPrice(panier.getTotalPrice());
        panierRepository.save(existingPanier);
    }

    @Override
    public void deletePanier(Long id) {
        panierRepository.deleteById(id);
    }
}
