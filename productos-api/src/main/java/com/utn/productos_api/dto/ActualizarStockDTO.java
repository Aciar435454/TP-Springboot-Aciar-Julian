package com.utn.productos_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Schema(description = "DTO para actualizar solo el stock de un producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarStockDTO {
    @Schema(description = "Nuevo valor de stock. Mínimo 0", example = "50")
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
