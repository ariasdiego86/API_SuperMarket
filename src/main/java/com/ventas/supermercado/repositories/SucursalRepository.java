package com.ventas.supermercado.repositories;

import com.ventas.supermercado.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//El primer tipo de dato de la clase mapeado y el segundo es el tipo de dato de la llave primaria
public interface SucursalRepository extends JpaRepository<Sucursal, UUID> {

}
