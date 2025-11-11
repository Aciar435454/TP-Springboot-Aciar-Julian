package com.utn.productos_api.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado.");
    }
}
