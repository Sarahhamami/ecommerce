package com.example.ecom.service;

import com.example.ecom.domain.Category;
import com.example.ecom.domain.PanierItem;

import java.util.List;

public interface PanierItemService {
    List<PanierItem> getAllPanierItem();
    PanierItem getPanierItem(Long id);
    void addPanierItem(PanierItem panierItem);
    void updatePanierItem(Long id, PanierItem panierItem);
    void deletePanierItem(Long id);
}
