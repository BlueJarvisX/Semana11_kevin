package com.andres.pasaportecafeovalle.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.andres.pasaportecafeovalle.data.local.dao.SucursalDao;
import com.andres.pasaportecafeovalle.data.local.db.DataBase;
import com.andres.pasaportecafeovalle.data.local.entities.SucursalEntity;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;

import java.util.ArrayList;
import java.util.List;

public class SucursalRepository {
    private final SucursalDao sucursalDao;

    public SucursalRepository(Application application) {
        DataBase db = DataBase.getInstance(application);
        sucursalDao = db.sucursalDao();
    }

    public LiveData<Integer> getSucursalCountLive() {
        return sucursalDao.getSucursalCountLive();
    }

    public void insertarSucursal(SucursalEntity sucursal) {
        DataBase.databaseWriteExecutor.execute(() -> sucursalDao.insertarSucursal(sucursal));
    }

    public void actualizarSucursal(SucursalEntity sucursal) {
        DataBase.databaseWriteExecutor.execute(() -> sucursalDao.actualizarSucursal(sucursal));
    }

    /**
     * Este método ahora devuelve un LiveData de SucursalModel, no de SucursalEntity.
     * La transformación ocurre aquí, manteniendo la capa de la UI limpia de entidades de Room.
     */
    public LiveData<List<SucursalModel>> listarSucursalLive() {
        // Transformations.map toma un LiveData<Input> y devuelve un LiveData<Output>
        return Transformations.map(
                sucursalDao.listarSucursalLive(), // <-- El LiveData de entrada (de SucursalEntity)
                listaDeEntidades -> { // <-- Función lambda que se ejecuta cuando los datos de entrada cambian
                    List<SucursalModel> listaDeModelos = new ArrayList<>();
                    for (SucursalEntity entity : listaDeEntidades) {
                        // Convertir cada SucursalEntity en un SucursalModel
                        SucursalModel model = new SucursalModel(
                                entity.getId(),
                                entity.getNombre(),
                                entity.getDireccion(),
                                entity.getLat(),
                                entity.getLot(),
                                entity.getHorario(), // El orden debe coincidir con el constructor de SucursalModel
                                entity.getEstado()
                        );
                        listaDeModelos.add(model);
                    }
                    return listaDeModelos; // <-- Devuelve la nueva lista de modelos
                }
        );
    }

    public LiveData<SucursalModel> listarSucursalPorId(int id){
        return Transformations.map(
                sucursalDao.listarSucursalPorId(id),
                entidad -> {
                    if (entidad == null) return null;
                    return new SucursalModel(
                            entidad.getId(),
                            entidad.getNombre(),
                            entidad.getDireccion(),
                            entidad.getLat(),
                            entidad.getLot(),
                            entidad.getHorario(),
                            entidad.getEstado()

                    );
                }
        );
    }
}
