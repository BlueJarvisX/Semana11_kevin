package com.andres.pasaportecafeovalle.view.modelo;

import java.util.Date;

public class Beneficio {
    private String nombre;
    private String tipo;
    private int requisito_visitas;

    private double descuento_pct;

    private Date vigencia_ini;

    private Date vigencia_fin;

    private String estado;

    public Beneficio(String nombre, String tipo, int requisito_visitas, double descuento_pct, Date vigencia_ini, Date vigencia_fin, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.requisito_visitas = requisito_visitas;
        this.descuento_pct = descuento_pct;
        this.vigencia_ini = vigencia_ini;
        this.vigencia_fin = vigencia_fin;
        this.estado = estado;
    }

    public Beneficio(String nombre, String tipo, int requisito_visitas, double descuento_pct, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.requisito_visitas = requisito_visitas;
        this.descuento_pct = descuento_pct;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public int getRequisito_visitas() {
        return requisito_visitas;
    }
    public double getDescuento_pct() {
        return descuento_pct;
    }
    public Date getVigencia_ini() {
        return vigencia_ini;
    }
    public Date getVigencia_fin() {
        return vigencia_fin;
    }
    public String getEstado() {
        return estado;
    }

}

