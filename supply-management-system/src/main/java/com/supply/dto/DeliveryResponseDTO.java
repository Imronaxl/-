package com.supply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supply.model.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с информацией о поставке")
public class DeliveryResponseDTO {
    
    @Schema(description = "ID поставки", example = "1")
    private Long id;
    
    @Schema(description = "Номер поставки", example = "DEL-2024-001")
    private String deliveryNumber;
    
    @Schema(description = "ID поставщика", example = "1")
    private Long supplierId;
    
    @Schema(description = "Название поставщика", example = "ООО Фруктовый рай")
    private String supplierName;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата поставки", example = "2024-01-15")
    private LocalDate deliveryDate;
    
    @Schema(description = "Общий вес поставки", example = "1500.750")
    private BigDecimal totalWeight;
    
    @Schema(description = "Общая стоимость поставки", example = "128625.00")
    private BigDecimal totalAmount;
    
    @Schema(description = "Примечания к поставке")
    private String notes;
    
    @Schema(description = "Статус поставки")
    private DeliveryStatus status;
    
    @Schema(description = "Элементы поставки")
    private List<DeliveryItemResponseDTO> items;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата создания")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Дата последнего обновления")
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Элемент поставки")
    public static class DeliveryItemResponseDTO {
        
        @Schema(description = "ID элемента поставки", example = "1")
        private Long id;
        
        @Schema(description = "ID продукта", example = "1")
        private Long productId;
        
        @Schema(description = "Название продукта", example = "Яблоки Голден")
        private String productName;
        
        @Schema(description = "Тип продукта", example = "APPLE")
        private String productType;
        
        @Schema(description = "Вес товара в кг", example = "100.500")
        private BigDecimal weight;
        
        @Schema(description = "Цена за кг", example = "85.75")
        private BigDecimal unitPrice;
        
        @Schema(description = "Общая стоимость товара", example = "8618.75")
        private BigDecimal totalPrice;
        
        @Schema(description = "Примечания к товару")
        private String notes;
    }
}
