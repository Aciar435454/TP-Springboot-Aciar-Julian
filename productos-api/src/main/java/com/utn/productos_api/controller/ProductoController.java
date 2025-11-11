package com.utn.productos_api.controller;

import com.utn.productos_api.dto.ActualizarStockDTO;
import com.utn.productos_api.dto.ProductoDTO;
import com.utn.productos_api.dto.ProductoResponseDTO;
import com.utn.productos_api.model.Producto;
import com.utn.productos_api.model.Categoria;
import com.utn.productos_api.service.ProductoService;
import com.utn.productos_api.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión completa de productos del sistema de E-commerce")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        );
    }

    private Producto toEntity(ProductoDTO dto) {
        return new Producto(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                dto.getStock(),
                dto.getCategoria()
        );
    }

    @Operation(summary = "Obtiene la lista completa de productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        List<ProductoResponseDTO> dtos = productoService.obtenerTodos().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Busca un producto por su identificador único (ID)")
    @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorld(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new com.utn.productos_api.exception.ProductoNotFoundException(id));

        return ResponseEntity.ok(toResponseDTO(producto));
    }

    @Operation(summary = "Filtra y obtiene productos por una categoría específica")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrada exitosamente")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable Categoria categoria) {
        List<ProductoResponseDTO> dtos = productoService.obtenerPorCategoria(categoria).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Crea un nuevo producto en el sistema")
    @ApiResponse(responseCode = "201", description = "Producto creado y retornado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (validación fallida)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = toEntity(productoDTO);
        Producto productoGuardado = productoService.crearProducto(nuevoProducto);
        return new ResponseEntity<>(toResponseDTO(productoGuardado), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza el producto completo por ID")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        Producto productoActualizado = productoService.actualizarProducto(id, toEntity(productoDTO));
        return ResponseEntity.ok(toResponseDTO(productoActualizado));
    }

    @Operation(summary = "Actualiza solo el stock de un producto específico (actualización parcial)")
    @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Stock inválido (ej: negativo)")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO stockDTO) {
        Producto productoActualizado = productoService.actualizarStock(id, stockDTO.getStock());
        return ResponseEntity.ok(toResponseDTO(productoActualizado));
    }

    @Operation(summary = "Elimina un producto por su ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado (No Content)")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}

