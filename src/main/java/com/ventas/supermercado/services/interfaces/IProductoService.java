package com.ventas.supermercado.services.interfaces;

/*
*   Se puede poner IProductoService y en los servicios ponerles de nombre: ProductoService
*   O dejar acá ProductoService y en los servicios ponerles de nombre: ProductoServiceImpl
*/


import com.ventas.supermercado.dtos.ProductoDTO;


import java.util.List;
import java.util.UUID;

public interface IProductoService {

    List<ProductoDTO> selectAllProductos();

    ProductoDTO createProducto(ProductoDTO productoDTO);

    ProductoDTO updateProducto(UUID id, ProductoDTO productoDTO);

    void deleteProducto(UUID id);
}
