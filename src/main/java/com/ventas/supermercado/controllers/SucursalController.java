package com.ventas.supermercado.controllers;

import com.ventas.supermercado.dtos.SucursalDTO;
import com.ventas.supermercado.services.interfaces.ISucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apiSuperMarket/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final ISucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> getAllSucursales ()
    {
        return ResponseEntity.ok(sucursalService.selectAllSucursales());
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> createSucursal (SucursalDTO sucursalDTO)
    {
        SucursalDTO sucursal = sucursalService.createSucursal(sucursalDTO);
        return ResponseEntity.created(URI.create("/api/sucursales/" + sucursal.getId()))
                .body(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> updateSucursal(@PathVariable UUID id, @RequestBody SucursalDTO sucursalDTO)
    {
        return ResponseEntity.ok(sucursalService.updateSucursal(id, sucursalDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable UUID id)
    {
        sucursalService.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }

}
