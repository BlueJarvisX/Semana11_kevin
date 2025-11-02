package com.andres.pasaportecafeovalle.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.andres.pasaportecafeovalle.data.local.entities.ClienteListado;
import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;

import java.util.List;

@Dao
public interface ClientesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarCliente(ClientesEntity clientes);

    @Query("SELECT COUNT(*) FROM clientes")
    LiveData<Integer> getClientesCountLive();

    @Query("SELECT id_cliente, nombre, email, telefono, fecha_nac, estado, creado_en FROM clientes")
    LiveData<List<ClienteListado>> listarClientesLive();

    @Update
    void editarCliente(ClientesEntity clientes);

    @Query("SELECT * FROM clientes WHERE email = :email LIMIT 1")
    ClientesEntity obtenerClientePorEmail(String email);

    @Query("SELECT id_cliente FROM clientes WHERE email = :email LIMIT 1")
    Integer obtenerIdClientePorEmail(String email);

    @Query("SELECT id FROM sucursal WHERE id = :id_sucursal LIMIT 1")
    Integer obtenerIdSucursalPorId(int id_sucursal);

}
