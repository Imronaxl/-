package com.supply.repository;

import com.supply.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    Optional<Supplier> findByInn(String inn);
    
    List<Supplier> findByActiveTrue();
    
    @Query("SELECT s FROM Supplier s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR s.inn LIKE CONCAT('%', :keyword, '%')")
    List<Supplier> searchByKeyword(String keyword);
}
