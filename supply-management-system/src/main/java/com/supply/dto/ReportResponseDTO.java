package com.supply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с отчетом")
public class ReportResponseDTO {
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата начала периода")
    private LocalDate startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата окончания периода")
    private LocalDate endDate;
    
    @Schema(description = "Общий вес по всем поставкам")
    private BigDecimal totalWeight;
    
    @Schema(description = "Общая стоимость по всем поставкам")
    private BigDecimal totalAmount;
    
    @Schema(description = "Количество поставок")
    private Integer totalDeliveries;
    
    @Schema(description = "Детализация по поставщикам и продуктам")
    private List<ReportItemDTO> items;
    
    @Schema(description = "Сводка по типам продуктов")
    private List<ProductTypeSummaryDTO> productTypeSummary;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Сводка по типу продукта")
    public static class ProductTypeSummaryDTO {
        
        @Schema(description = "Тип продукта", example = "APPLE")
        private String productType;
        
        @Schema(description = "Общий вес", example = "2500.500")
        private BigDecimal totalWeight;
        
        @Schema(description = "Общая стоимость", example = "215000.00")
        private BigDecimal totalAmount;
        
        @Schema(description = "Процент от общего объема", example = "60.5")
        private BigDecimal percentage;
    }
}
