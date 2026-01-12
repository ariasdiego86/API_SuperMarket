package com.ventas.supermercado.services;

import com.ventas.supermercado.dtos.ProductoDTO;
import com.ventas.supermercado.exceptions.NotFoundException;
import com.ventas.supermercado.mapper.Mapper;
import com.ventas.supermercado.models.Producto;
import com.ventas.supermercado.repositories.ProductoRepository;
import com.ventas.supermercado.services.interfaces.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/*
 * @RequiredArgsConstructor (Lombok): Genera automáticamente un constructor con argumentos en tiempo de compilación
 * para todos los campos declarados como 'final'.
 * Esto nos ahorra escribir el constructor manualmente.
 */
@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    /*
     * 1. Al ser 'final', Lombok sabe que debe incluir este campo en el constructor generado.
     * 2. Al haber un solo constructor en la clase, Spring 4.3+ inyecta la dependencia
     * automáticamente (Inyección por Constructor) sin necesidad de usar @Autowired.
     */
    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> selectAllProductos() {
        /*
         * EXPLICACIÓN DEL FLUJO (STREAM PIPELINE):
         * 1. findAll(): Trae TODOS los datos de la DB como una List<Producto>.
         * 2. stream(): Convierte esa lista en un flujo de datos (como una cinta transportadora)
         * para procesar los elementos uno por uno.
         * 3. map(Mapper::toDTO): OPERACIÓN INTERMEDIA. Transforma cada elemento.
         * - Entra: Entidad (Producto)
         * - Sale: DTO (ProductoDTO)
         * - "::" es una Referencia a Método (equivale a: producto -> Mapper.toDTO(producto))
         * 4. toList(): OPERACIÓN TERMINAL. Recolecta todos los DTOs transformados
         * y los vuelve a meter en una lista estándar de Java.
         */
        return productoRepository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList(); // En versiones de Java anteriores a la 16 se usaba .collect(Collectors.toList())
    }

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {

        Producto producto = Producto.builder()
                .productName(productoDTO.getProductName())
                .price(productoDTO.getPrice())
                .description(productoDTO.getDescription())
                .category(productoDTO.getCategory())
                .stock(productoDTO.getStock())
                .build();

        // Guardamos el producto en la base de datos y devolvemos el DTO del producto guardado
        return Mapper.toDTO(productoRepository.save(producto));//
    }

    @Override
    public ProductoDTO updateProducto(UUID id, ProductoDTO productoDTO) {

        //Verificar si el producto existe
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado par modificar, con el id: " + id));

        /* No usamos el Builder para actualizar porque ya tenemos la entidad existente
        *   y solo queremos modificar sus campos, no crear una nueva instancia.
        *   además, el Builder no nos permite modificar una instancia ya creada, solo crear nuevas.
        *  Y por último, al usar el Builder perderíamos la referencia al ID original del producto.
        *
        * */
        producto.setProductName(productoDTO.getProductName());
        producto.setPrice(productoDTO.getPrice());
        producto.setDescription(productoDTO.getDescription());
        producto.setCategory(productoDTO.getCategory());
        producto.setStock(productoDTO.getStock());

        return Mapper.toDTO(productoRepository.save(producto));

    }

    //Hacemos un borrado físico por ahora, pero lo ideal sería hacer un borrado lógico (cambiar un estado o flag)
    @Override
    public void deleteProducto(UUID id) {

        if (!productoRepository.existsById(id))
            throw new NotFoundException("Producto no encontrado para eliminar, con el id: " + id);

        productoRepository.deleteById(id);
    }
}
