package com.ventas.supermercado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupermercadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermercadoApplication.class, args);//TODO hacer experimentos cambiando las relaciones entre las entidades. Hacer preguntas a pete
        //TODO es decir, probar OneToMany, ManyToOne, OneToOne, ManyToMany. Invertir el orden en el que están, también probar las anotaciones de Cascade y Fetch
        //TODO para entender bien cómo funcionan y en qué casos se usan. Y comparar con las relaciones de la base de datos
        //TODO que pasa si en vez de usar la bidireccionalidad para prescindir de un repositorio como en este caso DetalleVentaRepository,
        //TODO creamos el repositorio y no hacemos la relación bidireccional, etc.
        //TODO repasar lo del mapper y uso del operador :: para referencias a métodos.
        // ? No entiendo por qué a en el curso están el campo "precio" en DetalleVenta y no se llama "subTotal", porque tiene más sentido que sea subTotal
        // ? Además, en detalleVentaDTO también está "subTotal", no "precio", solo "precioUnitario" y "cantProd.
    }

}
