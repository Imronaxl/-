package com.supply.controller;

import com.supply.dto.DeliveryRequestDTO;
import com.supply.dto.DeliveryResponseDTO;
import com.supply.model.DeliveryStatus;
import com.supply.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
@Tag(name = "Управление поставками", description = "API для работы с поставками")
public class DeliveryController {
    
    private final DeliveryService deliveryService;
    
    @PostMapping
    @Operation(summary = "Создать новую поставку")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Поставка успешно создана"),
        @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
        @ApiResponse(responseCode = "404", description = "Поставщик или продукт не найден")
    })
    public ResponseEntity<DeliveryResponseDTO> createDelivery(
            @Valid @RequestBody DeliveryRequestDTO request) {
        DeliveryResponseDTO response = deliveryService.createDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить поставку по ID")
    public ResponseEntity<DeliveryResponseDTO> getDelivery(
            @Parameter(description = "ID поставки") @PathVariable Long id) {
        DeliveryResponseDTO response = deliveryService.getDelivery(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/period")
    @Operation(summary = "Получить поставки за период")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesByPeriod(
            @Parameter(description = "Дата начала периода") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Дата окончания периода")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DeliveryResponseDTO> response = deliveryService.getDeliveriesByPeriod(startDate, endDate);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Получить поставки поставщика за период")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesBySupplierAndPeriod(
            @Parameter(description = "ID поставщика") @PathVariable Long supplierId,
            @Parameter(description = "Дата начала периода")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Дата окончания периода")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DeliveryResponseDTO> response = deliveryService.getDeliveriesBySupplierAndPeriod(
            supplierId, startDate, endDate);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Обновить статус поставки")
    public ResponseEntity<DeliveryResponseDTO> updateDeliveryStatus(
            @Parameter(description = "ID поставки") @PathVariable Long id,
            @Parameter(description = "Новый статус") @RequestParam DeliveryStatus status) {
        DeliveryResponseDTO response = deliveryService.updateDeliveryStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
