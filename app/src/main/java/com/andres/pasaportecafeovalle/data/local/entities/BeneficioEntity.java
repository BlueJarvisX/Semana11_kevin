package com.andres.pasaportecafeovalle.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "beneficios"
    , foreignKeys = {
    @ForeignKey(
        entity = ReglaEntity.class,
        parentColumns = "id_regla",
        childColumns = "id_regla",
        onDelete = ForeignKey.CASCADE
    ),
    @ForeignKey(
        entity = ProductoEntity.class,
        parentColumns = "id_producto",
        childColumns = "producto_premio",
        onDelete = ForeignKey.CASCADE
    )}
)
public class BeneficioEntity {

    @PrimaryKey(autoGenerate = true)
    private int id_beneficio;

    @ColumnInfo(name = "id_regla")
    private int id_regla;

    @ColumnInfo(name = "producto_premio")
    private int producto_premio;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "descripcion")
    private String tipo;

    @ColumnInfo(name = "requisito_visitas")
    private String requisito_visitas;

    @ColumnInfo(name = "descuento_pct")
    private float descuento_pct;

    @ColumnInfo(name = "vigencia_inicio")
    private String vigencia_inicio;

    @ColumnInfo(name = "vigencia_fin")
    private String vigencia_fin;

    @ColumnInfo(name = "estado")
    private String estado;

    public BeneficioEntity(int id_beneficio, int id_regla, int producto_premio, String nombre, String tipo, String requisito_visitas, float descuento_pct, String vigencia_inicio, String vigencia_fin, String estado) {
        this.id_beneficio = id_beneficio;
        this.id_regla = id_regla;
        this.producto_premio = producto_premio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.requisito_visitas = requisito_visitas;
        this.descuento_pct = descuento_pct;
        this.vigencia_inicio = vigencia_inicio;
        this.vigencia_fin = vigencia_fin;
        this.estado = estado;
    }

    public int getId_beneficio() {return id_beneficio;};
    public int getId_regla() {return id_regla;};
    public int getProducto_premio() {return producto_premio;};
    public String getNombre() {return nombre;};
    public String getTipo() {return tipo;};
    public String getRequisito_visitas() {return requisito_visitas;};
    public float getDescuento_pct() {return descuento_pct;};
    public String getVigencia_inicio() {return vigencia_inicio;};
    public String getVigencia_fin() {return vigencia_fin;};

    public String getEstado() {return estado;};



}
