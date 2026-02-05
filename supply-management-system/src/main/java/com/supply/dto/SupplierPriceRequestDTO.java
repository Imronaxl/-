package com.supply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на установку цены поставщика")
public class SupplierPriceRequestDTO {
    
    @NotNull(message = "ID поставщика обязателен")
    @Schema(description = "ID поставщика", example = "1")
    private Long supplierId;
    
    @NotNull(message = "ID продукта обязателен")
    @Schema(description = "ID продукта", example = "1")
    private Long productId;
    
    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @Digits(integer = 8, fraction = 2, message = "Некорректный формат цены")
    @Schema(description = "Цена за единицу", example = "85.50")
    private BigDecimal price;
    
    @NotNull(message = "Дата начала действия обязательна")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата начала действия цены", example = "2024-01-01")
    private LocalDate validFrom;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата окончания действия цены (опционально)")
    private LocalDate validTo;
}
