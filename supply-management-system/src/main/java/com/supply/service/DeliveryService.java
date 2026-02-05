package com.supply.service;

import com.supply.dto.DeliveryRequestDTO;
import com.supply.dto.DeliveryResponseDTO;
import com.supply.exception.ResourceNotFoundException;
import com.supply.exception.ValidationException;
import com.supply.model.*;
import com.supply.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
    
    private final DeliveryRepository deliveryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final DeliveryItemRepository deliveryItemRepository;
    private final SupplierPriceRepository supplierPriceRepository;
    
    @Transactional
    public DeliveryResponseDTO createDelivery(DeliveryRequestDTO request) {
        log.info("Creating delivery for supplier: {}", request.getSupplierId());
        
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
            .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));
        
        if (!supplier.isActive()) {
            throw new ValidationException("Supplier is not active");
        }
        
        String deliveryNumber = generateDeliveryNumber();
        
        Delivery delivery = Delivery.builder()
            .deliveryNumber(deliveryNumber)
            .supplier(supplier)
            .deliveryDate(request.getDeliveryDate())
            .notes(request.getNotes())
            .status(request.getStatus())
            .totalWeight(BigDecimal.ZERO)
            .totalAmount(BigDecimal.ZERO)
            .build();
        
        List<DeliveryItem> items = request.getItems().stream()
            .map(itemRequest -> {
                Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + itemRequest.getProductId()));
                
                return DeliveryItem.builder()
                    .delivery(delivery)
                    .product(product)
                    .weight(itemRequest.getWeight())
                    .unitPrice(itemRequest.getUnitPrice())
                    .notes(itemRequest.getNotes())
                    .build();
            })
            .collect(Collectors.toList());
        
        delivery.setItems(items);
        
        calculateDeliveryTotals(delivery);
        
        Delivery savedDelivery = deliveryRepository.save(delivery);
        log.info("Delivery created with number: {}", savedDelivery.getDeliveryNumber());
        
        return mapToResponseDTO(savedDelivery);
    }
    
    @Transactional(readOnly = true)
    public DeliveryResponseDTO getDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        
        return mapToResponseDTO(delivery);
    }
    
    @Transactional(readOnly = true)
    public List<DeliveryResponseDTO> getDeliveriesByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Delivery> deliveries = deliveryRepository.findByDeliveryDateBetween(startDate, endDate);
        
        return deliveries.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<DeliveryResponseDTO> getDeliveriesBySupplierAndPeriod(Long supplierId, 
                                                                     LocalDate startDate, 
                                                                     LocalDate endDate) {
        List<Delivery> deliveries = deliveryRepository.findBySupplierIdAndDeliveryDateBetween(
            supplierId, startDate, endDate);
        
        return deliveries.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public DeliveryResponseDTO updateDeliveryStatus(Long id, DeliveryStatus status) {
        Delivery delivery = deliveryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        
        delivery.setStatus(status);
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        
        log.info("Delivery {} status updated to {}", id, status);
        
        return mapToResponseDTO(updatedDelivery);
    }
    
    private String generateDeliveryNumber() {
        String year = String.valueOf(Year.now().getValue());
        Long count = deliveryRepository.countDeliveriesSince(
            LocalDate.of(Year.now().getValue(), 1, 1)) + 1;
        
        return String.format("DEL-%s-%04d", year, count);
    }
    
    private void calculateDeliveryTotals(Delivery delivery) {
        BigDecimal totalWeight = delivery.getItems().stream()
            .map(DeliveryItem::getWeight)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalAmount = delivery.getItems().stream()
            .map(DeliveryItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        delivery.setTotalWeight(totalWeight);
        delivery.setTotalAmount(totalAmount);
    }
    
    private DeliveryResponseDTO mapToResponseDTO(Delivery delivery) {
        List<DeliveryResponseDTO.DeliveryItemResponseDTO> itemDTOs = delivery.getItems().stream()
            .map(item -> DeliveryResponseDTO.DeliveryItemResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .productType(item.getProduct().getType().name())
                .weight(item.getWeight())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .notes(item.getNotes())
                .build())
            .collect(Collectors.toList());
        
        return DeliveryResponseDTO.builder()
            .id(delivery.getId())
            .deliveryNumber(delivery.getDeliveryNumber())
            .supplierId(delivery.getSupplier().getId())
            .supplierName(delivery.getSupplier().getName())
            .deliveryDate(delivery.getDeliveryDate())
            .totalWeight(delivery.getTotalWeight())
            .totalAmount(delivery.getTotalAmount())
            .notes(delivery.getNotes())
            .status(delivery.getStatus())
            .items(itemDTOs)
            .createdAt(delivery.getCreatedAt())
            .updatedAt(delivery.getUpdatedAt())
            .build();
    }
}
