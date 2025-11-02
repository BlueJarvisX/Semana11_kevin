package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "clientes")
public class ClientesEntity {
    @PrimaryKey(autoGenerate = true)
    private int id_cliente;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "clave")
    private String clave;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "fecha_nac")
    private String fecha_nac;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "creado_en")
    private String creado_en;

    public ClientesEntity(int id_cliente, String nombre, String email, String clave, String telefono, String fecha_nac, String estado, String creado_en) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }

    @Ignore
    public ClientesEntity(String nombre, String email, String clave, String telefono, String fecha_nac, String estado, String creado_en) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }

    public int getId_cliente() {return id_cliente;};

    public String getNombre() {return nombre;}

    public String getEmail() {return email;}
    public String getClave() {return clave;}

    public String getTelefono() {return telefono;}

    public String getFecha_nac() {return fecha_nac;}

    public String getEstado() {return estado;}

    public String getCreado_en() {return creado_en;}
}
