package com.ventas.supermercado.controllers;

import com.ventas.supermercado.dtos.ProductoDTO;
import com.ventas.supermercado.services.interfaces.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apiSuperMarket/productos")
@RequiredArgsConstructor // Lombok: genera un constructor con los campos `final` (inyección por constructor).
public class ProductoController {

    /*
     * Inyección por constructor (recomendada):
     * - @RequiredArgsConstructor genera un constructor con todos los campos `final`.
     * - Spring usa ese constructor para inyectar IProductoService.
     * Nota: no hace falta @Autowired ni escribir el constructor a mano.
     */
    private final IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos()
    {
        // Devuelve 200 OK con la lista; ResponseEntity permite controlar status/headers/cuerpo.
        List<ProductoDTO> productos = productoService.selectAllProductos();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO)
    {
        // @RequestBody: deserializa el JSON del cuerpo de la petición hacia ProductoDTO.
        ProductoDTO producto = productoService.createProducto(productoDTO);

        // 201 Created + header Location con la URL del recurso recién creado.
        // Ojo: se concatena el id al path base (idealmente con "/" entre ambos).
        return ResponseEntity.created(URI.create("/api/producto" + producto.getId())).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable UUID id, @RequestBody ProductoDTO productoDTO)
    {
        // @PathVariable toma el valor de {id} de la URL y lo pasa al parámetro del método.
        return ResponseEntity.ok(productoService.updateProducto(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable UUID id)
    {
        productoService.deleteProducto(id);
        // 204 No Content: operación exitosa sin cuerpo de respuesta.
        return ResponseEntity.noContent().build();
    }
}
