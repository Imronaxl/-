package com.supply.repository;

import com.supply.model.Product;
import com.supply.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByCode(String code);
    
    List<Product> findByType(ProductType type);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.code LIKE %:keyword%")
    List<Product> searchByKeyword(String keyword);
}
