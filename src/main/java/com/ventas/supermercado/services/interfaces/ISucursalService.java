package com.ventas.supermercado.services.interfaces;

import com.ventas.supermercado.dtos.SucursalDTO;

import java.util.List;
import java.util.UUID;

public interface ISucursalService {

    List<SucursalDTO> selectAllSucursales();

    SucursalDTO createSucursal(SucursalDTO sucursalDTO);

    SucursalDTO updateSucursal(UUID id, SucursalDTO sucursalDTO);

    void deleteSucursal(UUID id);


}
