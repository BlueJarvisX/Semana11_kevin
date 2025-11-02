package com.andres.pasaportecafeovalle.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.andres.pasaportecafeovalle.data.local.dao.VisitasDao;
import com.andres.pasaportecafeovalle.data.local.db.DataBase;
import com.andres.pasaportecafeovalle.data.local.entities.VisitaEntity;

import java.util.List;

public class VisitasRepository {

    private final VisitasDao visitasDao;

    public VisitasRepository(Application application) {
        DataBase db = DataBase.getInstance(application);
        visitasDao = db.visitasDao();
    }

    public void insertarVisita(VisitaEntity visita) {
        DataBase.databaseWriteExecutor.execute(() -> {
            visitasDao.insertarVisita(visita);
        });
    }

    public LiveData<List<VisitaEntity>> obtenerVisitasPorCliente(int id_cliente) {
        return visitasDao.obtenerVisitasPorCliente(id_cliente);
    }

    public LiveData<Integer> contarVisitasCliente(int id_cliente) {
        return visitasDao.contarVisitasCliente(id_cliente);
    }

    public VisitaEntity obtenerVisitaPorHash(String hash) {
        return visitasDao.obtenerVisitaPorHash(hash);
    }

    public void actualizarVisita(VisitaEntity visita) {
        DataBase.databaseWriteExecutor.execute(() -> visitasDao.actualizarVisita(visita));
    }
}
