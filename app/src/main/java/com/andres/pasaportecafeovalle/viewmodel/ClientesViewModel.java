package com.andres.pasaportecafeovalle.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.andres.pasaportecafeovalle.data.local.entities.ClienteListado;
import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;
import com.andres.pasaportecafeovalle.data.repository.ClientesRepositoty;
import com.andres.pasaportecafeovalle.view.fragment.admin.clientes.ClienteMapper;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;

import java.util.List;
import java.util.stream.Collectors;

public class ClientesViewModel extends AndroidViewModel {
    private final ClientesRepositoty clientesRepository;


    public ClientesViewModel(@NonNull Application application){
        super(application);
        clientesRepository = new ClientesRepositoty(application);
    }

    public LiveData<Integer> getClientesCountLive() {
        return clientesRepository.getClientesCountLive();
    }
    public void insertarClientes(ClientesModel clientes){
        try{
            ClientesEntity clientesEntity = ClienteMapper.toEntity(clientes);

            clientesRepository.insertarCliente(clientesEntity);
        }catch(Exception e) {
            Log.e("ClientesViewModel", "Error al insertar un cliente", e);
            throw e;
        }

    }

    public void editarCliente(ClientesModel clientes) {
        try{
            ClientesEntity clientesEntity = ClienteMapper.toEntity(clientes);
            clientesRepository.editarCliente(clientesEntity);
        }catch (Exception e){
            Log.e("ClientesViewModel", "Error al editar un cliente", e);
            throw e;
        }
    }

    public LiveData<List<ClientesModel>> listarClientesLive(){
        return clientesRepository.listarClientesLive();

    }

    public void login(String email, String clave, ClientesRepositoty.LoginCallback callback){
        clientesRepository.login(email, clave, callback);
    }

    public Integer obtenerIdClientePorEmail(String email) {
        return clientesRepository.obtenerIdClientePorEmail(email);
    }


}
