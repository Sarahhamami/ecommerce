package com.example.ecom.repository;

import com.example.ecom.domain.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepository extends JpaRepository<Panier,Long> {
}
