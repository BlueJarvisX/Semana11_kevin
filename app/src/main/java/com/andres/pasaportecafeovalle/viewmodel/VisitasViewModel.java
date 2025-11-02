package com.andres.pasaportecafeovalle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.andres.pasaportecafeovalle.data.local.entities.VisitaEntity;
import com.andres.pasaportecafeovalle.data.repository.ClientesRepositoty;
import com.andres.pasaportecafeovalle.data.repository.VisitasRepository;
import com.andres.pasaportecafeovalle.utils.Utils;

import java.util.List;

public class VisitasViewModel extends AndroidViewModel {

    private final VisitasRepository visitasRepository;

    public VisitasViewModel(@NonNull Application application){
        super(application);
        visitasRepository = new VisitasRepository(application);
    }

    public void insertarVisita(VisitaEntity visita) {
        visitasRepository.insertarVisita(visita);
    }

    public LiveData<List<VisitaEntity>> obtenerVisitasPorCliente(int idCliente) {
        return visitasRepository.obtenerVisitasPorCliente(idCliente);
    }

    public LiveData<Integer> contarVisitasCliente(int idCliente) {
        return visitasRepository.contarVisitasCliente(idCliente);
    }

    public VisitaEntity obtenerVisitaPorHash(String hash) {
        return visitasRepository.obtenerVisitaPorHash(hash);
    }

    public void actualizarVisita(VisitaEntity visita) {
        visitasRepository.actualizarVisita(visita);
    }

    public void generarYGuardarVisita(int idCliente, int idSucursal, String email, String origen) {
        String fechaActual = Utils.obtenerFechaActual(); // yyyy-MM-dd HH:mm:ss
        String hash = Utils.generarHashQR(idCliente, idSucursal);

        VisitaEntity visita = new VisitaEntity(
                idCliente,
                idSucursal,
                fechaActual,
                origen,
                "pendiente",  // estado inicial
                hash
        );

        insertarVisita(visita);
    }

}
