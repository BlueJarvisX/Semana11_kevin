package com.andres.pasaportecafeovalle.view.modelo;

public class Historial {

    private String nombreBeneficio;
    private String nombreSucursal;
    private String fechaHora;
    private String productoRelacionado;

    public Historial(String nombreBeneficio, String nombreSucursal, String fechaHora, String productoRelacionado) {
        this.nombreBeneficio = nombreBeneficio;
        this.nombreSucursal = nombreSucursal;
        this.fechaHora = fechaHora;
        this.productoRelacionado = productoRelacionado;
    }

    public String getNombreBeneficio() {
        return nombreBeneficio;
    }
    public String getNombreSucursal() {
        return nombreSucursal;
    }
    public String getFechaHora() {
        return fechaHora;
    }
    public String getProductoRelacionado() {
        return productoRelacionado;
    }


}
