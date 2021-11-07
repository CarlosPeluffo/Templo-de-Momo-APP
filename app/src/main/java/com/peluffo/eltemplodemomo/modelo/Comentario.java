package com.peluffo.eltemplodemomo.modelo;

import java.io.Serializable;

public class Comentario implements Serializable {
    private int id;
    private String fecha;
    private String cuerpo;
    private Usuario usuario;

    public Comentario(int id, String fecha, String cuerpo, Usuario usuario) {
        this.id = id;
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.usuario = usuario;
    }

    public Comentario(String fecha, String cuerpo, Usuario usuario) {
        this.fecha = fecha;
        this.cuerpo = cuerpo;
        this.usuario = usuario;
    }

    public Comentario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
