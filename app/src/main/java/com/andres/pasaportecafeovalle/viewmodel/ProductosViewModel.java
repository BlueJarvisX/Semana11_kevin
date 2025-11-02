package com.andres.pasaportecafeovalle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andres.pasaportecafeovalle.data.local.entities.ProductoEntity;
import com.andres.pasaportecafeovalle.data.repository.ProductoRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductosViewModel extends AndroidViewModel {
    private final ProductoRepository repository;
    private final MutableLiveData<List<ProductoEntity>> productosLiveData;
    private final ExecutorService executor;

    public ProductosViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductoRepository(application);
        productosLiveData = new MutableLiveData<>();
        executor = Executors.newFixedThreadPool(2);
        cargarProductos(); // Por defecto carga activos
    }

    public LiveData<List<ProductoEntity>> getProductos() {
        return productosLiveData;
    }

    public void cargarProductos() {
        executor.execute(() -> {
            List<ProductoEntity> productos = repository.obtenerProductosActivos();
            productosLiveData.postValue(productos);
        });
    }

    public void cargarTodosLosProductos() { // NUEVO
        executor.execute(() -> {
            List<ProductoEntity> productos = repository.obtenerTodosLosProductos();
            productosLiveData.postValue(productos);
        });
    }

    public void insertarProducto(ProductoEntity producto) {
        repository.insertarProducto(producto);
        cargarProductos();
    }

    public void actualizarProducto(ProductoEntity producto) {
        repository.actualizarProducto(producto);
        cargarProductos();
    }

    // Si no usas eliminarProducto desde la UI, puedes eliminarlo, pero lo dejo aquí por si lo necesitas:
    public void eliminarProducto(ProductoEntity producto) {
        repository.eliminarProducto(producto);
        cargarProductos();
    }
}
