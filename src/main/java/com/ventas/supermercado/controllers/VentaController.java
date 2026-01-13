package com.ventas.supermercado.controllers;

import com.ventas.supermercado.dtos.VentaDTO;
import com.ventas.supermercado.services.interfaces.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apiSuperMarket/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final IVentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> getAllVentas ()
    {
        return ResponseEntity.ok(ventaService.selectAllVentas());
    }

    /*
    *   Se crea una venta usando directamente VentaDTO en la request (opción simple, sin request separado)
    *   Se espera que el DTO traiga toda la información necesaria para crear la venta,
    *   incluyendo el detalle, como productos, cantidades, etc.
    * **/

    @PostMapping
    public ResponseEntity<VentaDTO> createVenta (@RequestBody  VentaDTO ventaDTO)
    {
        System.out.println("Suscursal ID recibida: " + ventaDTO.getSucursalId());
        VentaDTO venta = ventaService.createVenta(ventaDTO);
        return ResponseEntity.created(URI.create("/api/ventas/" + venta.getId()))
                .body(venta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> updateVenta(@PathVariable UUID id, @RequestBody VentaDTO ventaDTO)
    {
        //Actualiza fecha, estado, idSucursal, total, y reemplaza el detalle completo de la venta
        return ResponseEntity.ok(ventaService.updateVenta(id, ventaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable UUID id)
    {
        ventaService.deleteVenta(id);
        return ResponseEntity.noContent().build();
    }
}
