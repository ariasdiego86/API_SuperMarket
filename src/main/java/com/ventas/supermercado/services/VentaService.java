package com.ventas.supermercado.services;

import com.ventas.supermercado.dtos.DetalleVentaDTO;
import com.ventas.supermercado.dtos.VentaDTO;
import com.ventas.supermercado.exceptions.NotFoundException;
import com.ventas.supermercado.mapper.Mapper;
import com.ventas.supermercado.models.DetalleVenta;
import com.ventas.supermercado.models.Producto;
import com.ventas.supermercado.models.Sucursal;
import com.ventas.supermercado.models.Venta;
import com.ventas.supermercado.repositories.ProductoRepository;
import com.ventas.supermercado.repositories.SucursalRepository;
import com.ventas.supermercado.repositories.VentaRepository;
import com.ventas.supermercado.services.interfaces.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    @Override
    public List<VentaDTO> selectAllVentas() {

        List<Venta> ventas = ventaRepository.findAll();
        List<VentaDTO> ventasDTO = new ArrayList<>();

        //Leer abajo porque es mejor opción usar el Mapper
        //Es mejor usar streams como en los otros services
        for (Venta v : ventas)
            ventasDTO.add(Mapper.toDTO(v));

        return ventasDTO;
    }

    @Override
    public VentaDTO createVenta(VentaDTO ventaDTO) {

        //validaciones
        if(ventaDTO == null)
            throw new NotFoundException("VentaDTO es null");

        if (ventaDTO.getSucursalId() == null)
            throw new NotFoundException("SucursalId es null");

        if (ventaDTO.getDetalleVentaDto() == null || ventaDTO.getDetalleVentaDto().isEmpty())
            throw new NotFoundException("DetalleVenta es null o vacío, debe incluir al menos un producto");

        // Buscar la sucursal asociada
        Sucursal sucursal = sucursalRepository.findById(ventaDTO.getSucursalId())
                .orElseThrow( () -> new NotFoundException("Sucursal no encontrada con el id: " + ventaDTO.getSucursalId()));

        //Crear la entidad Venta
        Venta venta = Venta.builder()
                .date(ventaDTO.getDate())
                .total(ventaDTO.getTotal())
                .status(ventaDTO.getStatus())
                .sucursal(sucursal)
                .build();

        //Lista de detalleVenta
        List<DetalleVenta> detalleVenta = new ArrayList<>();

        for (DetalleVentaDTO detDTO : ventaDTO.getDetalleVentaDto())
        {
            //Buscar el producto por ID es lo mejor
            //Acá lo hacemos por nombre para ocupar el método personalizado en ProductoRepository
            Producto producto = productoRepository.findByProductName(detDTO.getProductName())
                    .orElseThrow(
                            () -> new NotFoundException("Producto no encontrado con el nombre: " + detDTO.getProductName())
                    );

            //Crear el DetalleVenta
            //? Preguntar por qué acá al setear venta no es necesario que venta lleve la lista de detallesVenta ya seteada.
            DetalleVenta detalle = DetalleVenta.builder()
                    .producto(producto)
                    .venta(venta)//* No tenemos que poner varias ventas porque la relación es ManyToOne en DetalleVenta
                    .cantProd(detDTO.getCantProd())
                    .subTotal(producto.getPrice().multiply(BigDecimal.valueOf(detDTO.getCantProd())))
                    .build();

            detalleVenta.add(detalle);
        }
        //Se setea la lista de detalleVenta
        venta.setDetalleVenta(detalleVenta);

        //Guardar la venta (JPA guardará automáticamente los detalles por la relación bidireccional)
        return Mapper.toDTO(ventaRepository.save(venta));
    }

    @Override
    public VentaDTO updateVenta(UUID id, VentaDTO ventaDTO) {

        Venta venta = ventaRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Venta no encontrada con el id: " + id));

        if(ventaDTO.getDate() != null)
            venta.setDate(ventaDTO.getDate());

        if (ventaDTO.getStatus() != null)
            venta.setStatus(ventaDTO.getStatus());

        if (ventaDTO.getTotal() != null)
            venta.setTotal(ventaDTO.getTotal());

        if (ventaDTO.getSucursalId() != null)
        {
            Sucursal sucursal = sucursalRepository.findById(ventaDTO.getSucursalId())
                    .orElseThrow( () -> new NotFoundException("Sucursal no encontrada con el id: " + ventaDTO.getSucursalId()));

            venta.setSucursal(sucursal);
        }

        return Mapper.toDTO(ventaRepository.save(venta));
    }

    @Override
    public void deleteVenta(UUID id) {
        ventaRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Venta no encontrada con el id: " + id));

        ventaRepository.deleteById(id);
    }
}

// No recomendado, mejor usar el Mapper, ya que usando el mapper tenemos muchas ventajas y es la forma más limpia de hacerlo
        /*Ventajas:
                SRP / separación de responsabilidades: el service no sabe cómo se arma el DTO.
                Si mañana cambia VentaDTO (agregas/quitas campos), cambias solo el Mapper, no todos los services.
                Evitas duplicar mapeos en muchos métodos (selectAllVentas, selectById, etc.).
        *
        * */
        /*for (Venta v : ventas)
        {
            VentaDTO ventaDTO = VentaDTO.builder()
                    .id(v.getId())
                    .date(v.getDate())
                    .total(v.getTotal())
                    .status(v.getStatus())
                    .sucursalId(v.getSucursal().getId())
                    .build();

            ventasDTO.add(ventaDTO);
        }*/