package com.supply.service;

import com.supply.dto.SupplierPriceRequestDTO;
import com.supply.exception.ResourceNotFoundException;
import com.supply.model.Product;
import com.supply.model.Supplier;
import com.supply.model.SupplierPrice;
import com.supply.repository.ProductRepository;
import com.supply.repository.SupplierPriceRepository;
import com.supply.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService {
    
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final SupplierPriceRepository supplierPriceRepository;
    
    @Transactional
    public SupplierPrice setSupplierPrice(SupplierPriceRequestDTO request) {
        log.info("Setting price for supplier: {}, product: {}", 
                request.getSupplierId(), request.getProductId());
        
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Supplier not found with id: " + request.getSupplierId()));
        
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Product not found with id: " + request.getProductId()));
        
        supplierPriceRepository.findCurrentPrice(
            supplier.getId(), 
            product.getId(), 
            LocalDate.now()
        ).ifPresent(previousPrice -> {
            previousPrice.setValidTo(request.getValidFrom().minusDays(1));
            supplierPriceRepository.save(previousPrice);
            log.info("Closed previous price for supplier: {}, product: {}", 
                    supplier.getId(), product.getId());
        });
        
        SupplierPrice newPrice = SupplierPrice.builder()
            .supplier(supplier)
            .product(product)
            .price(request.getPrice())
            .validFrom(request.getValidFrom())
            .validTo(request.getValidTo())
            .build();
        
        SupplierPrice savedPrice = supplierPriceRepository.save(newPrice);
        log.info("New price set: {}", savedPrice);
        
        return savedPrice;
    }
    
    @Transactional(readOnly = true)
    public List<SupplierPrice> getSupplierPriceHistory(Long supplierId, Long productId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + supplierId);
        }
        
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        
        return supplierPriceRepository.findPriceHistory(supplierId, productId);
    }
    
    @Transactional(readOnly = true)
    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByActiveTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Supplier> searchSuppliers(String keyword) {
        return supplierRepository.searchByKeyword(keyword);
    }
}
