package com.ventas.supermercado.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
/*
 * REGLAS DE CONSTRUCTORES:
 * 1. @AllArgsConstructor: Es OBLIGATORIO para @Builder. El patrón Builder necesita
 * un constructor público con todos los argumentos para generar el objeto final.
 * 2. @NoArgsConstructor: Es OBLIGATORIO para Hibernate/JPA (Entidades) y Jackson (JSON).
 * Estas librerías usan Reflexión para instanciar objetos vacíos antes de llenar sus datos, necesita el constuctor
 * vacío sin parámetros.
 */

/*
 * EXPLICACIÓN DEL "COMBO" DE CONSTRUCTORES:
 * 1. @AllArgsConstructor: OBLIGATORIO para @Builder.
 * - El patrón Builder necesita internamente un constructor con todos los parámetros para funcionar.
 * - ALERTA: Al agregar este (o cualquier otro constructor), Java ELIMINA automáticamente el constructor vacío por defecto.
 *
 * 2. @NoArgsConstructor: OBLIGATORIO para Hibernate/JPA.
 * - Hibernate necesita instanciar la clase vacía (usando new Clase()) antes de mapear los datos.
 * - Como @AllArgsConstructor borró el constructor vacío de Java, debemos agregarlo explícitamente con esta anotación.
 */
@AllArgsConstructor// Requisito de @Builder: Necesita este constructor para poder crear el objeto con todos sus datos
@NoArgsConstructor// Requisito de JPA/Jackson: Necesitan instanciar la clase vacía antes de setear valores (Reflexión).
@Builder
/* * @Builder: Implementa el patrón de diseño "Builder".
 * Nos permite crear objetos complejos paso a paso usando métodos encadenados
 * (ej: .id(1).name("Pan").build()) en lugar de un constructor gigante
 * o muchos setters. Hace el código más legible al crear instancias.
 */
public class ProductoDTO {

    private UUID id;
    private String productName;
    private BigDecimal price;
    private String description;
    private String category;
    private int stock;
}
