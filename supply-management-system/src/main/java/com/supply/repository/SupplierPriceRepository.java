package com.supply.repository;

import com.supply.model.SupplierPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierPriceRepository extends JpaRepository<SupplierPrice, Long> {
    
    @Query("SELECT sp FROM SupplierPrice sp " +
           "WHERE sp.supplier.id = :supplierId " +
           "AND sp.product.id = :productId " +
           "AND (sp.validTo IS NULL OR sp.validTo >= :date) " +
           "AND sp.validFrom <= :date " +
           "ORDER BY sp.validFrom DESC")
    Optional<SupplierPrice> findCurrentPrice(@Param("supplierId") Long supplierId,
                                            @Param("productId") Long productId,
                                            @Param("date") LocalDate date);
    
    @Query("SELECT sp FROM SupplierPrice sp " +
           "WHERE sp.supplier.id = :supplierId " +
           "AND sp.product.id = :productId " +
           "ORDER BY sp.validFrom DESC")
    List<SupplierPrice> findPriceHistory(@Param("supplierId") Long supplierId,
                                        @Param("productId") Long productId);
}
