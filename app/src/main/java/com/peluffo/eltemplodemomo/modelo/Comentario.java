package com.peluffo.eltemplodemomo.modelo;

import java.io.Serializable;

public class Comentario implements Serializable {
    private int id;
    private String fecha;
    private String cuerpo;
    private Usuario usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
