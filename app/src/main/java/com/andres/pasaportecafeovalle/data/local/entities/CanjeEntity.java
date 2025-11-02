package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "canjes",
        foreignKeys = {
            @ForeignKey(
                    entity = ClientesEntity.class,
                    parentColumns = "id_cliente",
                    childColumns = "id_cliente",
                    onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                    entity = BeneficioEntity.class,
                    parentColumns = "id_beneficio",
                    childColumns = "id_beneficio",
                    onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                    entity = SucursalEntity.class,
                    parentColumns = "id",
                    childColumns = "id_sucursal",
                    onDelete = ForeignKey.CASCADE
            )
        })
public class CanjeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id_canje;

    @ColumnInfo(name = "id_cliente")
    private int id_cliente;

    @ColumnInfo(name = "id_beneficio")
    private int id_beneficio;

    @ColumnInfo(name = "id_sucursal")
    private int id_sucursal;

    @ColumnInfo(name = "fecha_hora")
    private String fecha_hora;

    @ColumnInfo(name = "codigo_otp")
    private String codigo_otp;

    @ColumnInfo(name = "estado_sync")
    private String estado_sync;

    public CanjeEntity(int id_canje, int id_cliente, int id_beneficio, int id_sucursal, String fecha_hora, String codigo_otp, String estado_sync) {
        this.id_canje = id_canje;
        this.id_cliente = id_cliente;
        this.id_beneficio = id_beneficio;
        this.id_sucursal = id_sucursal;
        this.fecha_hora = fecha_hora;
        this.codigo_otp = codigo_otp;
        this.estado_sync = estado_sync;
    }

    public int getId_canje() { return id_canje; }
    public int getId_cliente() { return id_cliente; }
    public int getId_beneficio() { return id_beneficio; }
    public int getId_sucursal() { return id_sucursal; }
    public String getFecha_hora() { return fecha_hora; }
    public String getCodigo_otp() { return codigo_otp; }
    public String getEstado_sync() { return estado_sync; }


}
