package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "visitas",
        foreignKeys = {
                @ForeignKey(
                        entity = ClientesEntity.class,
                        parentColumns = "id_cliente",
                        childColumns = "id_cliente",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = SucursalEntity.class,
                        parentColumns = "id",
                        childColumns = "id_sucursal",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class VisitaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id_visita;

    @ColumnInfo(name = "id_cliente")
    private int id_cliente;

    @ColumnInfo(name = "id_sucursal")
    private int id_sucursal;

    @ColumnInfo(name = "fecha_visita")
    private String fecha_visita;

    @ColumnInfo(name = "origen")
    private String origen;

    @ColumnInfo(name = "estado_sync")
    private String estado_sync;

    @ColumnInfo(name = "hash_qr")
    private String hash_qr;

    @ColumnInfo(name = "estado")
    private String estado;

    public VisitaEntity(int id_visita, int id_cliente, int id_sucursal, String fecha_visita, String origen, String estado_sync, String hash_qr, String estado) {
        this.id_visita = id_visita;
        this.id_cliente = id_cliente;
        this.id_sucursal = id_sucursal;
        this.fecha_visita = fecha_visita;
        this.origen = origen;
        this.estado_sync = estado_sync;
        this.hash_qr = hash_qr;
        this.estado = estado;
    }

    @Ignore
    public VisitaEntity(int id_cliente, int id_sucursal, String fecha_visita, String origen, String estado, String hash_qr) {
        this.id_cliente = id_cliente;
        this.id_sucursal = id_sucursal;
        this.fecha_visita = fecha_visita;
        this.origen = origen;
        this.estado = estado;
        this.hash_qr = hash_qr;
    }

    public int getId_visita() {
        return id_visita;
    }
    public int getId_cliente() {
        return id_cliente;
    }
    public int getId_sucursal() {
        return id_sucursal;
    }
    public String getFecha_visita() {
        return fecha_visita;
    }
    public String getOrigen() {
        return origen;
    }
    public String getEstado_sync() {
        return estado_sync;
    }
    public String getHash_qr() {
        return hash_qr;
    }
    public String getEstado() {return estado;}
    public void setEstado(String estado) { this.estado = estado; }

}
