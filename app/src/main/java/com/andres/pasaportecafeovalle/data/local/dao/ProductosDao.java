package com.andres.pasaportecafeovalle.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;

import java.util.List;

@Dao
public interface ProductosDao {
    @Insert
    void insertarProducto(ProductoEntity producto);

    @Update
    void actualizarProducto(ProductoEntity producto);

    @Delete
    void eliminarProducto(ProductoEntity producto);

    @Query("SELECT * FROM productos WHERE estado = 'Activo'")
    List<ProductoEntity> obtenerProductosActivos();

    @Query("SELECT * FROM productos")
    List<ProductoEntity> obtenerTodosLosProductos();

    @Query("SELECT * FROM productos WHERE id_producto = :id")
    ProductoEntity obtenerProductoPorId(int id);
}
