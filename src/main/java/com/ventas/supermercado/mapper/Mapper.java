package com.ventas.supermercado.mapper;

import com.ventas.supermercado.dtos.DetalleVentaDTO;
import com.ventas.supermercado.dtos.ProductoDTO;
import com.ventas.supermercado.dtos.SucursalDTO;
import com.ventas.supermercado.dtos.VentaDTO;
import com.ventas.supermercado.models.Producto;
import com.ventas.supermercado.models.Sucursal;
import com.ventas.supermercado.models.Venta;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/*
 * Convierte una Entidad (base de datos) a un DTO (respuesta API).
 * Usamos el patrón Builder del DTO para asignar los valores de forma limpia.
 * static: No necesitamos instanciar la clase Mapper para usar este método.
 */
public class Mapper {


    //Mapeo de DTOs a Entidades y viceversa. Declaramos los métodos como estáticos para poder usarlos sin instanciar la clase Mapper.

    //Mapeo de Producto a ProductoDTO

    public static ProductoDTO toDTO(Producto producto) {

        if (producto == null) return null;
        //builder() crea una instancia del constructor Builder del DTO, necesitamos la anotación @AllArgsConstructor y @Builder en el DTO
        return ProductoDTO.builder()
                .id(producto.getId())
                .productName(producto.getProductName())
                .price(producto.getPrice())
                .description(producto.getDescription())
                .category(producto.getCategory())
                .stock(producto.getStock())
                .build(); // Empaqueta y devuelve el objeto final
    }

    //Mapeo de Sucursal a SucursalDTO y sobreescribimos el método toDTO

    public static SucursalDTO toDTO (Sucursal sucursal)
    {
        if (sucursal == null) return null;

        return SucursalDTO.builder()
                .id(sucursal.getId())
                .localName(sucursal.getLocalName())
                .address(sucursal.getAddress())
                .build();
    }

    //Mapeo de Venta a VentaDTO
    public static VentaDTO toDTO (Venta venta)
    {
        if (venta == null) return null;

        // 1. PROCESAMIENTO DE LOS DETALLES (PRODUCTOS)
        // .stream(): Convierte la lista en una "finta de montaje" para procesar dato por dato.
        var detalleVenta = venta.getDetalleVenta()
                .stream()
                .map( detalleV ->  // .map(): Transforma cada elemento 'DetalleVenta' (Entidad) en un 'DetalleVentaDTO'.
                        DetalleVentaDTO.builder()
                                .id(detalleV.getId())
                                .productName(detalleV.getProducto().getProductName())
                                .cantProd(detalleV.getCantProd())
                                .precioUnitario(detalleV.getProducto().getPrice())

                                // BigDecimal.valueOf(): Convierte el int (cantidad) a BigDecimal de forma segura.
                                // .multiply(): En BigDecimal no se usa '*', se usan métodos como multiply, add, subtract.
                                .subTotal(detalleV.getProducto().getPrice().multiply(BigDecimal.valueOf(detalleV.getCantProd())))
                                .build()

                ).toList();
        /* Java 16+: Se usa .toList() para retornar una lista inmutable (más segura para DTOs)
         y optimizar memoria frente a .collect(Collectors.toList())
            LOS DTOs deben ser inmutables para evitar modificaciones accidentales
         */

        // 2. CÁLCULO DEL TOTAL
        // map() Extrae solo los subtotales de la lista de DTOs ya creada.
        // .reduce(): "Reduce" la lista a un solo valor (la suma).
        var total = detalleVenta.stream()
                .map(DetalleVentaDTO::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);// BigDecimal::add es una referencia al método 'add' de BigDecimal
        // Con BigDecimal.ZERO indicamos que el valor inicial de la suma es (0)


        // 3. CONSTRUCCIÓN FINAL DEL DTO PADRE
        return VentaDTO.builder()
                .id(venta.getId())
                .date(venta.getDate())
                .status(venta.getStatus())

                // Operador ternario: Si hay sucursal, saca el ID; si no, pon null. Evita NullPointerException.
                .sucursalId(venta.getSucursal() != null ? venta.getSucursal().getId() : null)
                .detalleVentaDto(detalleVenta)
                .total(total) // Usamos el total calculado arriba
                .build();

        /*
        * El Operador :: (Referencia a Método)
            Es un atajo sintáctico (syntactic sugar) para escribir lambdas más limpias.

            Código largo: .map(dto -> dto.getSubTotal())

            Con :: .map(DetalleVentaDTO::getSubTotal) Ambos hacen lo mismo, pero el segundo dice: "De la clase DetalleVentaDTO, usa el método getSubTotal".
        * */
    }
}
