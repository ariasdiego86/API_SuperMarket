package com.ventas.supermercado.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor// Para @Builder Necesita un constructor para crear el objeto final con todos sus datos de golpe.
@NoArgsConstructor// JPA / Hibernate (y Jackson): Necesitan instanciar la clase vacía antes de llenar los datos.
@Builder
@Entity // Indica que esta clase es una entidad JPA y se mapeará a una tabla de la base de datos.
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;//Al usar UUID, el tipo de dato debe ser UUID, no String. Si usamos String, JPA no lo mapeará bien. y consumirá más memoria.
    private String productName;
    private BigDecimal price;
    private String description;
    private String category;
    private int stock;
}

/*
* Si estás usando Entidades (JPA),
* a veces verás que recomiendan poner access = AccessLevel.PROTECTED en el constructor vacío,
* para evitar crear objetos vacíos por error en tu código, pero permitiendo que Hibernate lo use.

Se vería así (solo para Entidades, no hace falta en DTOs): @NoArgsConstructor(access = AccessLevel.PROTECTED)

Pero por ahora, con la versión simple (@NoArgsConstructor) te funcionará perfecto.*/