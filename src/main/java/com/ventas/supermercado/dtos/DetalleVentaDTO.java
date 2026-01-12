package com.ventas.supermercado.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVentaDTO {

    private UUID id;
    private String productName;
    private Integer cantProd;
    private BigDecimal precioUnitario;
    private BigDecimal subTotal;//Campo calculado, no existe en la tabla es un campo extra en el DTO

}
