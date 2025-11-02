package com.andres.pasaportecafeovalle.data.repository;

import android.app.Application;

import com.andres.pasaportecafeovalle.data.local.dao.ProductosDao;
import com.andres.pasaportecafeovalle.data.local.db.DataBase;
import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductoRepository {
    private final ProductosDao productosDao;
    private final ExecutorService executor;

    public ProductoRepository(Application application) {
        DataBase db = DataBase.getInstance(application);
        productosDao = db.productosDao();
        executor = Executors.newFixedThreadPool(2);
    }

    public void insertarProducto(ProductoEntity producto) {
        executor.execute(() -> productosDao.insertarProducto(producto));
    }

    public void actualizarProducto(ProductoEntity producto) {
        executor.execute(() -> productosDao.actualizarProducto(producto));
    }

    public void eliminarProducto(ProductoEntity producto) {
        executor.execute(() -> productosDao.eliminarProducto(producto));
    }

    public List<ProductoEntity> obtenerProductosActivos() {
        return productosDao.obtenerProductosActivos();
    }

    public List<ProductoEntity> obtenerTodosLosProductos() { // NUEVO
        return productosDao.obtenerTodosLosProductos();
    }

    public ProductoEntity obtenerProductoPorId(int id) {
        return productosDao.obtenerProductoPorId(id);
    }
}
