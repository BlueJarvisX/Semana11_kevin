package com.andres.pasaportecafeovalle.view.modelo;

public class Usuario {

    private int id_usuario;
    private int id_sucursal;
    private String email;
    private String clave;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id_usuario, int id_sucursal, String email, String clave, String rol) {
        this.id_usuario = id_usuario;
        this.id_sucursal = id_sucursal;
        this.email = email;
        this.clave = clave;
        this.rol = rol;

    }

    public Usuario(int id_usuario, String email, String clave, String rol) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.clave = clave;
        this.rol = rol;

    }

    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_sucursal() {
        return id_sucursal;
    }
    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }


}
