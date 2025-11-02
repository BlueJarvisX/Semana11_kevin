package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "regla")
public class ReglaEntity {
    @PrimaryKey(autoGenerate = true)
    private int id_regla;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "expresion")
    private String expresion;


    public ReglaEntity(int id_regla, String descripcion, String expresion) {
        this.id_regla = id_regla;
        this.descripcion = descripcion;
        this.expresion = expresion;
    }

    public int getId_regla() {
        return id_regla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getExpresion() {
        return expresion;
    }
}
