package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "producto_listado")
public class ProductoListado {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_producto")
    private int idProducto;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "categoria")
    private String categoria;

    @ColumnInfo(name = "precio")
    private float precio;

    @ColumnInfo(name = "estado")
    private String estado;

    public ProductoListado(int idProducto, String nombre, String categoria, float precio, String estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
