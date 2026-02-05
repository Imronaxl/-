package com.supply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supply.model.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
@Schema(description = "Запрос на создание поставки")
public class DeliveryRequestDTO {
    
    @NotNull(message = "ID поставщика обязателен")
    @Schema(description = "ID поставщика", example = "1")
    private Long supplierId;
    
    @NotNull(message = "Дата поставки обязательна")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата поставки", example = "2024-01-15")
    private LocalDate deliveryDate;
    
    @Size(max = 1000, message = "Примечание не должно превышать 1000 символов")
    @Schema(description = "Примечания к поставке")
    private String notes;
    
    @NotNull(message = "Статус поставки обязателен")
    @Schema(description = "Статус поставки")
    private DeliveryStatus status;
    
    @NotEmpty(message = "Список товаров не может быть пустым")
    @Valid
    @Schema(description = "Список товаров в поставке")
    private List<DeliveryItemRequestDTO> items;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Элемент поставки")
    public static class DeliveryItemRequestDTO {
        
        @NotNull(message = "ID продукта обязателен")
        @Schema(description = "ID продукта", example = "1")
        private Long productId;
        
        @NotNull(message = "Вес обязателен")
        @DecimalMin(value = "0.001", message = "Вес должен быть больше 0")
        @Digits(integer = 7, fraction = 3, message = "Некорректный формат веса")
        @Schema(description = "Вес товара в кг", example = "100.500")
        private BigDecimal weight;
        
        @NotNull(message = "Цена за единицу обязательна")
        @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
        @Digits(integer = 8, fraction = 2, message = "Некорректный формат цены")
        @Schema(description = "Цена за кг", example = "85.75")
        private BigDecimal unitPrice;
        
        @Size(max = 500, message = "Примечание не должно превышать 500 символов")
        @Schema(description = "Примечания к товару")
        private String notes;
    }
}
