package com.supply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для формирования отчета")
public class ReportRequestDTO {
    
    @NotNull(message = "Дата начала периода обязательна")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата начала периода", example = "2024-01-01")
    private LocalDate startDate;
    
    @NotNull(message = "Дата окончания периода обязательна")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата окончания периода", example = "2024-01-31")
    private LocalDate endDate;
    
    @Schema(description = "ID поставщика (опционально)")
    private Long supplierId;
    
    @Schema(description = "Тип продукта (опционально)")
    private String productType;
}
