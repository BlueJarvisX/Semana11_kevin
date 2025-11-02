package com.andres.pasaportecafeovalle.view.modelo;

import java.io.Serializable;

public class Producto implements Serializable {

    private int id_producto;
    private String nombre;
    private String categoria;
    private float precio;
    private String estado;
    private int image;

    public Producto(int id_producto, String nombre, String categoria, float precio, String estado, int image) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.image = image;
    }

    public int getId_producto() { return id_producto; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public float getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public int getImage() { return image; }
}
