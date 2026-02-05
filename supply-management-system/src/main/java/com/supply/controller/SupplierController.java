package com.supply.controller;

import com.supply.dto.SupplierPriceRequestDTO;
import com.supply.model.Supplier;
import com.supply.model.SupplierPrice;
import com.supply.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@Tag(name = "Управление поставщиками", description = "API для работы с поставщиками")
public class SupplierController {
    
    private final SupplierService supplierService;
    
    @PostMapping("/prices")
    @Operation(summary = "Установить цену поставщика на продукт")
    public ResponseEntity<SupplierPrice> setSupplierPrice(
            @Valid @RequestBody SupplierPriceRequestDTO request) {
        SupplierPrice price = supplierService.setSupplierPrice(request);
        return ResponseEntity.ok(price);
    }
    
    @GetMapping("/{supplierId}/products/{productId}/prices")
    @Operation(summary = "Получить историю цен поставщика на продукт")
    public ResponseEntity<List<SupplierPrice>> getPriceHistory(
            @PathVariable Long supplierId,
            @PathVariable Long productId) {
        List<SupplierPrice> prices = supplierService.getSupplierPriceHistory(supplierId, productId);
        return ResponseEntity.ok(prices);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Получить список активных поставщиков")
    public ResponseEntity<List<Supplier>> getActiveSuppliers() {
        List<Supplier> suppliers = supplierService.getActiveSuppliers();
        return ResponseEntity.ok(suppliers);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Поиск поставщиков")
    public ResponseEntity<List<Supplier>> searchSuppliers(
            @RequestParam String keyword) {
        List<Supplier> suppliers = supplierService.searchSuppliers(keyword);
        return ResponseEntity.ok(suppliers);
    }
}
