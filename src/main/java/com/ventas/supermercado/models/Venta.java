package com.ventas.supermercado.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate date;
    private BigDecimal total;
    private String status;

    //Relación con la tabla Sucursal, MUCHAS ventas pueden pertenecer a UNA sucursal y UNA sucursal puede tener MUCHAS ventas
    /* También se puede ver como: UNA sucursal tiene MUCHAS ventas y CADA (UNA) venta pertenece a UNA sucursal
        (mi proceso mental para armar el esquema de la BD en mi mente) */
    @ManyToOne
    private Sucursal sucursal;

    /* Relación con la tabla DetalleVenta, UNA venta puede tener MUCHOS detalles de venta asociados (OneToMany) y
        Muchos detalle de venta solo pueden pertenecer a UNA venta asociada (ManyToOne)
        También se puede ver como: UNA venta puede tener MUCHOS detalles de venta asociados y
        CADA (UN) detalle de venta solo puede pertenecer a UNA venta (mi proceso mental para armar el esquema de la BD en mi mente)
     */

    /**Tenemos que mapear la relación bidireccional entre Venta y DetalleVenta. Para prescindir de hacer un DetalleVentaRepository
     ya qué JPA detecta la relación y "sabe" que hay una tabla intermedia y puede manejarla automáticamente si nosotros hacemos la
     relación bidireccional, es decir poner OneToMany de un lado y del otro ManyToOne */

    //Tenemos que indicar con qué atributo de la clase DetalleVenta va mapeado, en este caso con el atributo "venta" en la clase DetalleVenta
    //el nombre "venta" tiene que coincidir exactamente con el nombre del atributo en DetalleVenta al que estamos mapeando
    /*CascadaType.ALL para que al guardar una Venta se guarden automáticamente sus DetalleVenta asociados
    y si se elimina una Venta se eliminen sus DetalleVenta asociados
    (va en "cascada" si eliminamos el dato padre se eliminan los hijos asociados a este)*/
    /*orphanRemoval = true: si hay algún registro huérfano (DetalleVenta sin Venta asociada) se elimina automáticamente
        es decir si hay registro de tablas hijas que deberían estar asociados a registros de tablas padres que ya no existen, esos registros huérfanos se eliminan
     */
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleVenta> detalleVenta = new ArrayList<>();

    /*
    * La diferencia entre FetchType.EAGER y FetchType.LAZY es que EAGER carga los datos relacionados inmediatamente al cargar la entidad principal,
    * mientras que LAZY carga los datos relacionados solo cuando se accede a ellos por primera
    * **/
    //El tipo de dato también debe ser el de la clase en donde se encuentra el campo "venta", en este caso DetalleVenta



}
