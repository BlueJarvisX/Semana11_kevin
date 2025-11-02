package com.andres.pasaportecafeovalle.view.modelo;


public class ClientesModel {

    private int id_cliente;

    private String nombre;
    private String email;

    private String clave;
    private String telefono;
    private String fecha_nac;
    private String estado;

    private String creado_en;

    public ClientesModel() {
    }

    public ClientesModel(String nombre, String email, String telefono, String fecha_nac, String estado, String creado_en) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }

    public ClientesModel(String nombre, String email, String clave, String telefono, String fecha_nac, String estado, String creado_en) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }

    public ClientesModel(int id_cliente, String nombre, String email, String clave, String telefono, String fecha_nac, String estado, String creado_en) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }


    public ClientesModel(int id_cliente, String nombre, String email, String telefono, String fecha_nac, String estado, String creado_en) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_nac = fecha_nac;
        this.estado = estado;
        this.creado_en = creado_en;
    }

    public ClientesModel(int clienteId, String nombre, String email, String telefono, String fechaNac) {
        this.id_cliente = clienteId;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_nac = fechaNac;
    }

    public int getId_cliente() {return id_cliente;}

    public String getNombre() {
        return nombre;
    }

    public String getEmail(){
        return email;
    }
    public String getClave(){return clave;}
    public String getTelefono(){
        return telefono;
    }
    public String getFecha_nac(){
        return fecha_nac;
    }

    public String getEstado(){
        return estado;
    }

    public String getCreado_en(){
        return creado_en;
    }

    public void setId_cliente(int id_cliente) {this.id_cliente = id_cliente;}
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public void setFecha_nac(String fecha_nac) {this.fecha_nac = fecha_nac;}

    public void setEstado(String estado) {this.estado = estado;}
    public void setCreado_en(String creado_en) {this.creado_en = creado_en;}



}
