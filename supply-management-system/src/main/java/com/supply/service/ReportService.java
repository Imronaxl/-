package com.supply.service;

import com.supply.dto.*;
import com.supply.repository.DeliveryItemRepository;
import com.supply.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    
    private final DeliveryItemRepository deliveryItemRepository;
    private final DeliveryRepository deliveryRepository;
    
    @Transactional(readOnly = true)
    public ReportResponseDTO generateReport(ReportRequestDTO request) {
        log.info("Generating report for period: {} to {}", request.getStartDate(), request.getEndDate());
        
        validateReportRequest(request);
        
        List<ReportItemDTO> reportItems = deliveryItemRepository.generateReport(
            request.getStartDate(),
            request.getEndDate(),
            request.getSupplierId(),
            request.getProductType()
        );
        
        List<ReportResponseDTO.ProductTypeSummaryDTO> typeSummary = calculateProductTypeSummary(
            request.getStartDate(), request.getEndDate(), reportItems);
        
        BigDecimal totalWeight = reportItems.stream()
            .map(ReportItemDTO::getTotalWeight)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalAmount = reportItems.stream()
            .map(ReportItemDTO::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        List<String> deliveries = deliveryRepository.findByDeliveryDateBetween(
            request.getStartDate(), request.getEndDate()).stream()
            .map(d -> d.getDeliveryNumber())
            .collect(Collectors.toList());
        
        return ReportResponseDTO.builder()
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .totalWeight(totalWeight)
            .totalAmount(totalAmount)
            .totalDeliveries(deliveries.size())
            .items(reportItems)
            .productTypeSummary(typeSummary)
            .build();
    }
    
    private void validateReportRequest(ReportRequestDTO request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        if (request.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
    }
    
    private List<ReportResponseDTO.ProductTypeSummaryDTO> calculateProductTypeSummary(
            LocalDate startDate, LocalDate endDate, List<ReportItemDTO> reportItems) {
        
        List<Object[]> rawSummary = deliveryItemRepository.getProductTypeSummary(startDate, endDate);
        
        BigDecimal grandTotal = reportItems.stream()
            .map(ReportItemDTO::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return rawSummary.stream()
            .map(row -> {
                String type = (String) row[0];
                BigDecimal weight = (BigDecimal) row[1];
                BigDecimal amount = (BigDecimal) row[2];
                
                BigDecimal percentage = grandTotal.compareTo(BigDecimal.ZERO) > 0
                    ? amount.divide(grandTotal, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                    : BigDecimal.ZERO;
                
                return ReportResponseDTO.ProductTypeSummaryDTO.builder()
                    .productType(type)
                    .totalWeight(weight)
                    .totalAmount(amount)
                    .percentage(percentage)
                    .build();
            })
            .collect(Collectors.toList());
    }
}
