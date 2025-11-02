package com.andres.pasaportecafeovalle.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.andres.pasaportecafeovalle.data.local.entities.VisitaEntity;

import java.util.List;

@Dao
public interface VisitasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarVisita(VisitaEntity visita);

    @Query("SELECT * FROM visitas WHERE id_cliente = :idCliente ORDER BY fecha_visita DESC")
    LiveData<List<VisitaEntity>> obtenerVisitasPorCliente(int idCliente);

    @Query("SELECT COUNT(*) FROM visitas WHERE id_cliente = :idCliente")
    LiveData<Integer> contarVisitasCliente(int idCliente);

    @Query("SELECT * FROM visitas WHERE hash_qr = :hash LIMIT 1")
    VisitaEntity obtenerVisitaPorHash(String hash);

    @Update
    void actualizarVisita(VisitaEntity visita);
}
