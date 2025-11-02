package com.andres.pasaportecafeovalle.data.local.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productos")
public class ProductoEntity {

    @PrimaryKey(autoGenerate = true)
    private int id_producto;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "precio")
    private float precio;

    @ColumnInfo(name = "estado")
    private String estado;

    public ProductoEntity(int id_producto, String nombre, String categoria, float precio, String estado) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
    }
    public int getId_producto() {
        return id_producto;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public float getPrecio() {
        return precio;
    }
    public String getEstado() {
        return estado;
    }

}
