package com.ventas.supermercado.dtos;

import lombok.*;

import java.util.UUID;

@Getter @Setter //Lombok genera los getters y setters
@AllArgsConstructor//Lombok genera el constructor con todos los atributos
@NoArgsConstructor// Lombok genera el constructor vacío
@Builder
public class SucursalDTO {

    //En los DTOs no se ponen las anotaciones de JPA
    private UUID id;
    private String localName;
    private String address;
}
