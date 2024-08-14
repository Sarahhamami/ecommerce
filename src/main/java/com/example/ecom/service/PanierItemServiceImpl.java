package com.example.ecom.service;

import com.example.ecom.CustomException.ResourceNotFoundException;
import com.example.ecom.domain.Category;
import com.example.ecom.domain.PanierItem;
import com.example.ecom.repository.PanierItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PanierItemServiceImpl implements PanierItemService{
    private PanierItemRepository panierItemRepository;
    @Override
    public List<PanierItem> getAllPanierItem() {
        return panierItemRepository.findAll();
    }

    @Override
    public PanierItem getPanierItem(Long id) {
        return panierItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PanierItem not found with id " + id));
    }

    @Override
    public void addPanierItem(PanierItem panierItem) {
        panierItemRepository.save(panierItem);
    }

    @Override
    public void updatePanierItem(Long id, PanierItem panierItem) {
        PanierItem existingPanierItem = panierItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Panier item not found with id " + id));
        existingPanierItem.setPanier(panierItem.getPanier());
        existingPanierItem.setQuantity(panierItem.getQuantity());
        existingPanierItem.setProductId(panierItem.getProductId());
        panierItemRepository.save(existingPanierItem);
    }

    @Override
    public void deletePanierItem(Long id) {
        panierItemRepository.deleteById(id);
    }
}
