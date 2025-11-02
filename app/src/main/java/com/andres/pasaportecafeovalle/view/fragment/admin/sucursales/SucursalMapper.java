package com.andres.pasaportecafeovalle.view.fragment.admin.sucursales;

import com.andres.pasaportecafeovalle.data.local.entities.SucursalEntity;
import com.andres.pasaportecafeovalle.view.modelo.SucursalModel;

public class SucursalMapper {
    public static SucursalEntity toEntity(SucursalModel model){
        SucursalEntity entity = new SucursalEntity(
                model.getNombre(),
                model.getDireccion(),
                model.getLat(),
                model.getLot(),
                model.getEstado(),
                model.getHorario());
        return entity;
    }
    public static SucursalModel toModel(SucursalEntity entity){
        SucursalModel model = new SucursalModel(
                entity.getNombre(),
                entity.getDireccion(),
                entity.getLat(),
                entity.getLot(),
                entity.getEstado(),
                entity.getHorario());
        return model;
    }
}
