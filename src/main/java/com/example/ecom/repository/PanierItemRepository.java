package com.example.ecom.repository;

import com.example.ecom.domain.PanierItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierItemRepository extends JpaRepository<PanierItem, Long> {
}
