package com.example.ecom.service;

import com.example.ecom.domain.Panier;

import java.util.List;

public interface PanierService {
    List<Panier> getAllPanier();
    Panier getPanier(Long id);
    void addPanier(Panier panier);
    void updatePanier(Long id, Panier panier);
    void deletePanier(Long id);
}
