package com.ventas.supermercado.services;

import com.ventas.supermercado.dtos.SucursalDTO;
import com.ventas.supermercado.exceptions.NotFoundException;
import com.ventas.supermercado.mapper.Mapper;
import com.ventas.supermercado.models.Sucursal;
import com.ventas.supermercado.repositories.SucursalRepository;
import com.ventas.supermercado.services.interfaces.ISucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SucursalService implements ISucursalService {

    private final SucursalRepository sucursalRepository;

    @Override
    public List<SucursalDTO> selectAllSucursales() {
        return sucursalRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public SucursalDTO createSucursal(SucursalDTO sucursalDTO) {

        Sucursal sucursal = Sucursal.builder()
                .localName(sucursalDTO.getLocalName())
                .address(sucursalDTO.getAddress())
                .build();

        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public SucursalDTO updateSucursal(UUID id, SucursalDTO sucursalDTO) {

        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Sucursal no encontrada con el id: " + id));

        sucursal.setLocalName(sucursalDTO.getLocalName());
        sucursal.setAddress(sucursalDTO.getAddress());

        return Mapper.toDTO(sucursalRepository.save(sucursal));
    }

    @Override
    public void deleteSucursal(UUID id) {

        if (!sucursalRepository.existsById(id))
            throw new NotFoundException("Sucursal no encontrada con el id: " + id);

        sucursalRepository.deleteById(id);
    }
}
