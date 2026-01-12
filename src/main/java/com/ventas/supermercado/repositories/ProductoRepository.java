package com.ventas.supermercado.repositories;

import com.ventas.supermercado.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

// El primer tipo de dato es la clase mapeada y el segundo es el tipo de dato de la llave primaria
public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    // --- IMPORTANTE (Spring Data JPA): cómo “lee” el nombre del método ---
    // Spring Data NO usa el nombre del parámetro para decidir por qué campo filtrar.
    // Lo que usa es el *nombre de la propiedad* que aparece después de "findBy".
    //
    // En  Producto tenemos el campo: private String productName;
    // Por eso, "findByProductName" significa: WHERE productName = ? (va a filtrar por productName)
    // * Esto es un método personalizado, específicamente es un Métodos Derivados (Query Methods)
    // * Es la forma más sencilla, donde Spring genera automáticamente la consulta SQL basándose únicamente en el nombre del método.
    Optional<Producto> findByProductName(String productName);

    // Si ponemos "findByName", Spring Data intentaría buscar una propiedad llamada "name".
    // Como en Producto NO existe "name" (existe "productName"), esto fallaría al arrancar
    // con un error del tipo: "No property name found for type Producto".
    //
    // Optional<Producto> findByName(String name);







    // --- ¿Y si quiero ponerle CUALQUIER NOMBRE al método, pero que busque por productName? ---
    // En ese caso, no puedes depender del parser de nombres (findBy...).
    // La práctica común es declarar la consulta explícitamente con @Query.
    //
    // Nota: En JPQL se usan nombres de la *clase* y *propiedades Java*, no nombres de tabla/columna.
    // (Por eso usamos "Producto" y "p.productName".)
    //    @Query("SELECT p FROM Producto p WHERE p.productName = :productName")
    //    Optional<Producto> buscarProductoPorNombrePersonalizado(@Param("productName") String productName);

}
