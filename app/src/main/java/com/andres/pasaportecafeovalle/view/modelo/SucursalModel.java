package com.andres.pasaportecafeovalle.view.modelo;

public class SucursalModel {

    private int id;
    private int imagen;
    private String nombre;
    private String direccion;

    private double lat;
    private double lot;

    private String horario;

    private String estado;

    public SucursalModel(int id, String nombre, String direccion, double lat, double lot, String horario, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat = lat;
        this.lot = lot;
        this.horario = horario;
        this.estado = estado;
    }

    public SucursalModel(String nombre, String direccion, double lat, double lot, String horario, String estado) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
