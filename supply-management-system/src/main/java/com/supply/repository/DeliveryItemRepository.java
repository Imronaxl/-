package com.supply.repository;

import com.supply.dto.ReportItemDTO;
import com.supply.model.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryItemRepository extends JpaRepository<DeliveryItem, Long> {
    
    List<DeliveryItem> findByDeliveryId(Long deliveryId);
    
    @Query("SELECT NEW com.supply.dto.ReportItemDTO(" +
           "s.id, s.name, p.id, p.name, p.type, " +
           "SUM(di.weight), SUM(di.totalPrice), " +
           "AVG(di.unitPrice), COUNT(DISTINCT d.id)) " +
           "FROM DeliveryItem di " +
           "JOIN di.delivery d " +
           "JOIN d.supplier s " +
           "JOIN di.product p " +
           "WHERE d.deliveryDate BETWEEN :startDate AND :endDate " +
           "AND (:supplierId IS NULL OR s.id = :supplierId) " +
           "AND (:productType IS NULL OR p.type = :productType) " +
           "GROUP BY s.id, s.name, p.id, p.name, p.type " +
           "ORDER BY s.name, p.name")
    List<ReportItemDTO> generateReport(@Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate,
                                      @Param("supplierId") Long supplierId,
                                      @Param("productType") String productType);
    
    @Query("SELECT p.type, SUM(di.weight), SUM(di.totalPrice) " +
           "FROM DeliveryItem di " +
           "JOIN di.delivery d " +
           "JOIN di.product p " +
           "WHERE d.deliveryDate BETWEEN :startDate AND :endDate " +
           "GROUP BY p.type")
    List<Object[]> getProductTypeSummary(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}
