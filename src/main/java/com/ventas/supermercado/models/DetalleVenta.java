package com.ventas.supermercado.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //Relación con la tabla Venta, MUCHOS detalles de venta pueden pertenecer a UNA venta (ManyToOne) y UNA venta puede tener MUCHOS detalles de venta (OneToMany)
    /*También se puede ver como: UNA venta puede tener MUCHOS detalles de venta asociados y
     CADA (UN) detalle de venta solo puede pertenecer a UNA venta (mi proceso mental para armar el esquema en mi mente) */
    @ManyToOne
    private Venta venta;

    //Relación con la tabla Producto, MUCHOS detalles de venta pueden pertenecer a UN producto (ManyToOne) y UN producto puede tener MUCHOS detalles de venta (OneToMany)
    /*También se puede ver como: UN producto puede estar en MUCHOS detalles de venta, y
       CADA (UN) detalle de venta solo puede tener UN producto asociado (mi proceso mental)
    */
    @ManyToOne
    private Producto producto;

    private Integer cantProd;// cantidad de producto vendido
    private BigDecimal subTotal;// precio total por la cantidad de producto vendido (precioUnitario * cantProd)


    //TODO
    //Es mejor poner el precio unitario en producto, porque si el precio del producto cambia en el futuro,
    // el detalle de la venta seguiría teniendo el precio correcto en el momento de la venta.
    //private Double precioUnitario;


}
