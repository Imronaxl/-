package com.supply.repository;

import com.supply.model.Delivery;
import com.supply.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    Optional<Delivery> findByDeliveryNumber(String deliveryNumber);
    
    List<Delivery> findBySupplierIdAndDeliveryDateBetween(Long supplierId, 
                                                         LocalDate startDate, 
                                                         LocalDate endDate);
    
    List<Delivery> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Delivery> findByStatus(DeliveryStatus status);
    
    @Query("SELECT COUNT(d) FROM Delivery d WHERE d.deliveryDate >= :date")
    Long countDeliveriesSince(@Param("date") LocalDate date);
}
