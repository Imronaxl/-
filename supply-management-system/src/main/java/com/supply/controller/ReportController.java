package com.supply.controller;

import com.supply.dto.ReportRequestDTO;
import com.supply.dto.ReportResponseDTO;
import com.supply.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Отчеты", description = "API для генерации отчетов")
public class ReportController {
    
    private final ReportService reportService;
    
    @PostMapping("/deliveries")
    @Operation(summary = "Сгенерировать отчет по поставкам")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Отчет успешно сгенерирован"),
        @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
    })
    public ResponseEntity<ReportResponseDTO> generateDeliveryReport(
            @Valid @RequestBody ReportRequestDTO request) {
        ReportResponseDTO report = reportService.generateReport(request);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/deliveries/quick")
    @Operation(summary = "Быстрый отчет за текущий месяц")
    public ResponseEntity<ReportResponseDTO> getCurrentMonthReport() {
        ReportRequestDTO request = ReportRequestDTO.builder()
            .startDate(LocalDate.now().withDayOfMonth(1))
            .endDate(LocalDate.now())
            .build();
        
        ReportResponseDTO report = reportService.generateReport(request);
        return ResponseEntity.ok(report);
    }
}
