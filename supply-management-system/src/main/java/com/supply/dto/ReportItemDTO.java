package com.supply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Элемент отчета")
public class ReportItemDTO {
    
    @Schema(description = "ID поставщика", example = "1")
    private Long supplierId;
    
    @Schema(description = "Название поставщика", example = "ООО Фруктовый рай")
    private String supplierName;
    
    @Schema(description = "ID продукта", example = "1")
    private Long productId;
    
    @Schema(description = "Название продукта", example = "Яблоки Голден")
    private String productName;
    
    @Schema(description = "Тип продукта", example = "APPLE")
    private String productType;
    
    @Schema(description = "Общий вес", example = "1500.750")
    private BigDecimal totalWeight;
    
    @Schema(description = "Общая стоимость", example = "128625.00")
    private BigDecimal totalAmount;
    
    @Schema(description = "Средняя цена за кг", example = "85.75")
    private BigDecimal averagePrice;
    
    @Schema(description = "Количество поставок", example = "5")
    private Integer deliveryCount;
}
