package com.ventas.supermercado.services.interfaces;

import com.ventas.supermercado.dtos.VentaDTO;

import java.util.List;
import java.util.UUID;

public interface IVentaService {

    List<VentaDTO> selectAllVentas();

    VentaDTO createVenta(VentaDTO ventaDTO);

    VentaDTO updateVenta(UUID id, VentaDTO ventaDTO);

    void deleteVenta(UUID id);
}
