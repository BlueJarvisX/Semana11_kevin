package com.andres.pasaportecafeovalle.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.andres.pasaportecafeovalle.data.local.entities.SucursalEntity;

import java.util.List;

@Dao
public interface SucursalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarSucursal(SucursalEntity sucursal);

    /**
     * Room usará la clave primaria del objeto 'sucursal' para encontrar y actualizar la fila correcta.
     */
    @Update
    void actualizarSucursal(SucursalEntity sucursal); // esta linea se debera añadir depues

    @Query("SELECT COUNT(*) FROM sucursal")
    LiveData<Integer> getSucursalCountLive();

    /**
     * Se añade "ORDER BY nombre ASC" para que la lista se muestre siempre en orden alfabético.
     * Es una buena práctica para tener una UI mas consistente.
     */
    @Query("SELECT * FROM sucursal ORDER BY nombre ASC")
    // <-- MEJORA SUGERIDA
    LiveData<List<SucursalEntity>> listarSucursalLive();

    @Query("SELECT * FROM sucursal WHERE id = :id ORDER BY nombre ASC")
        // <-- MEJORA SUGERIDA
    LiveData<SucursalEntity> listarSucursalPorId(int id);

}
