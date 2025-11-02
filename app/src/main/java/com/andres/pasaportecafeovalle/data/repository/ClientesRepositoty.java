package com.andres.pasaportecafeovalle.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.andres.pasaportecafeovalle.data.local.dao.ClientesDao;
import com.andres.pasaportecafeovalle.data.local.db.DataBase;
import com.andres.pasaportecafeovalle.data.local.entities.ClienteListado;
import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;
import com.andres.pasaportecafeovalle.utils.Utils;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;

import java.util.ArrayList;
import java.util.List;

public class ClientesRepositoty {
    private final ClientesDao clientesDao;

    public ClientesRepositoty(Application application) {
        DataBase db = DataBase.getInstance(application);
        clientesDao = db.clientesDao();
    }

    public LiveData<Integer> getClientesCountLive() {
        return clientesDao.getClientesCountLive();
    }
    public void insertarCliente(ClientesEntity clientes) {
        DataBase.databaseWriteExecutor.execute(() -> clientesDao.insertarCliente(clientes));
    }

    public void editarCliente(ClientesEntity clientes) {
        DataBase.databaseWriteExecutor.execute(() -> clientesDao.editarCliente(clientes));
    }

    public LiveData<List<ClientesModel>> listarClientesLive() {
        return Transformations.map(
                clientesDao.listarClientesLive(),
                list -> {
                    List<ClientesModel> models = new ArrayList<>();
                    for (ClienteListado c : list) {
                        ClientesModel model = new ClientesModel();
                        model.setId_cliente(c.id_cliente);
                        model.setNombre(c.nombre);
                        model.setEmail(c.email);
                        model.setTelefono(c.telefono);
                        model.setFecha_nac(c.fecha_nac);
                        model.setEstado(c.estado);
                        model.setCreado_en(c.creado_en);
                        models.add(model);
                    }
                    return models;
                }
        );
    }

    public void login(String email, String clave, LoginCallback callback){
        DataBase.databaseWriteExecutor.execute(() -> {
            ClientesEntity cliente = clientesDao.obtenerClientePorEmail(email);

            if (cliente != null){
                String hash = Utils.hashPassword(clave);
                if (hash.equals(cliente.getClave())){
                    callback.onSuccess(cliente);
                } else {
                    callback.onFailure("Contraseña incorrecta");
                }
            }else {
                callback.onFailure("Usuario no encontrado");
            }
        });
    }

    public Integer obtenerIdClientePorEmail(String email) {
        return clientesDao.obtenerIdClientePorEmail(email);
    }

    public Integer obtenerIdSucursalPorId(int id_sucursal) {
        return clientesDao.obtenerIdSucursalPorId(id_sucursal);
    }

    public interface LoginCallback {
        void onSuccess(ClientesEntity cliente);
        void onFailure(String mensajeError);
    }


}
