package com.andres.pasaportecafeovalle.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.andres.pasaportecafeovalle.data.local.entities.SucursalEntity;
import com.andres.pasaportecafeovalle.data.repository.SucursalRepository;
import com.andres.pasaportecafeovalle.view.fragment.admin.sucursales.SucursalMapper;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;

import java.util.List;

public class SucursalViewModel extends AndroidViewModel {
    private final SucursalRepository sucursalRepository;
    // Guardamos la referencia al LiveData para no pedirla cada vez
    private final LiveData<List<SucursalModel>> listaSucursales;

    public SucursalViewModel(@NonNull Application application){
        super(application);
        sucursalRepository = new SucursalRepository(application);
        // El repositorio ya nos da el LiveData en el formato que necesitamos (Model)
        listaSucursales = sucursalRepository.listarSucursalLive();
    }

    public LiveData<Integer> getSucursalCountLive() {
        return sucursalRepository.getSucursalCountLive();
    }

    /**
     * Inserta una nueva sucursal. Convierte el Model a Entity antes de pasarlo al repositorio.
     */
    public void insertarSucursal(SucursalModel sucursal){
        try{
            SucursalEntity sucursalEntity = SucursalMapper.toEntity(sucursal);
            sucursalRepository.insertarSucursal(sucursalEntity);
        }catch(Exception e) {
            Log.e("SucursalViewModel", "Error al insertar una Sucursal", e);
            throw e; // Re-lanzar la excepción para que pueda ser capturada si es necesario
        }
    }

    public void editarSucursal(SucursalModel sucursal) {
        try {
            SucursalEntity sucursalEntity = SucursalMapper.toEntity(sucursal);
            sucursalEntity.setId(sucursal.getId()); // ¡MUY IMPORTANTE! Asignar el ID para la actualización.

            sucursalRepository.actualizarSucursal(sucursalEntity);
        } catch (Exception e) {
            Log.e("SucursalViewModel", "Error al editar una Sucursal", e);
            throw e;
        }
    }


    /**
     * Expone el LiveData de la lista de sucursales a la UI.
     * Ahora este método es mucho más simple.
     */
    public LiveData<List<SucursalModel>> listarSucursalLive(){
        return listaSucursales; // Simplemente devuelve el LiveData que ya tenemos
    }

    public LiveData<SucursalModel> listarSucursalPorId(int id){
        return sucursalRepository.listarSucursalPorId(id);
    }
}
