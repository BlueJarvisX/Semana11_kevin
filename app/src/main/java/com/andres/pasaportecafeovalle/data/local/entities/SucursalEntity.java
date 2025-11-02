package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sucursal")
public class SucursalEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "direccion")
    private String direccion;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lot;

    @ColumnInfo(name = "horario")
    private String horario;

    @ColumnInfo(name = "estado")
    private String estado;

    public SucursalEntity(String nombre, String direccion, double lat, double lot, String horario, String estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat = lat;
        this.lot = lot;
        this.horario = horario;
        this.estado = estado;
    }

    // Si se necesita para pruebas, se puede dejar con @Ignore.
    @Ignore
    public SucursalEntity(int id, String nombre, String direccion, double lat, double lot, String horario, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat = lat;
        this.lot = lot;
        this.horario = horario;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLot() { return lot; }
    public void setLot(double lot) { this.lot = lot; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

}
