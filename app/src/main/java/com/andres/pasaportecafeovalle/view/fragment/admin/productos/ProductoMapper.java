package com.andres.pasaportecafeovalle.view.fragment.admin.productos;

import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;
import com.andres.pasaportecafeovalle.view.modelo.Producto;

public class ProductoMapper {
    public static Producto entityToModel(ProductoEntity entity) {
        return new Producto(
                entity.getId_producto(),
                entity.getNombre(),
                entity.getCategoria(),
                entity.getPrecio(),
                entity.getEstado(),
                0
        );
    }

    public static ProductoEntity modelToEntity(Producto producto) {
        return new ProductoEntity(
                producto.getId_producto(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getEstado()
        );
    }
}
