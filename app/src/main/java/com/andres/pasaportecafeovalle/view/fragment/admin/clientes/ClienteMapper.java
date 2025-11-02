package com.andres.pasaportecafeovalle.view.fragment.admin.clientes;

import com.andres.pasaportecafeovalle.data.local.entities.ClientesEntity;
import com.andres.pasaportecafeovalle.utils.Utils;
import com.andres.pasaportecafeovalle.view.modelo.ClientesModel;

public class ClienteMapper {
    public static ClientesEntity toEntity(ClientesModel model){
        if (model.getId_cliente() > 0) {
            String claveHash = Utils.hashPassword(model.getClave());
            return new ClientesEntity(
                    model.getId_cliente(),
                    model.getNombre(),
                    model.getEmail(),
                    claveHash,
                    model.getTelefono(),
                    model.getFecha_nac(),
                    model.getEstado(),
                    model.getCreado_en());
        } else {
            String claveHash = Utils.hashPassword(model.getClave());
            return new ClientesEntity(
                    model.getNombre(),
                    model.getEmail(),
                    claveHash,
                    model.getTelefono(),
                    model.getFecha_nac(),
                    model.getEstado(),
                    model.getCreado_en());
        }
    }

    public static ClientesModel toModel(ClientesEntity entity){
        ClientesModel model = new ClientesModel(
                entity.getId_cliente(),
                entity.getNombre(),
                entity.getEmail(),
                entity.getClave(),
                entity.getTelefono(),
                entity.getFecha_nac(),
                entity.getEstado(),
                entity.getCreado_en());
        return model;
    }
}
