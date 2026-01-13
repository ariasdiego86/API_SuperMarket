package com.ventas.supermercado.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTO {

    //Atributos que ya están en la clase Venta pero sin anotaciones de JPA
    private UUID id;
    private LocalDate date;
    private String status;

    //Atributos de las relaciones
    private UUID sucursalId;

    private List<DetalleVentaDTO> detalleVentaDto;

    private BigDecimal total;
}
