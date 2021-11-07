package com.peluffo.eltemplodemomo.modelo;


import java.io.Serializable;

public class Noticia implements Serializable {
    private int id;
    private String fecha;
    private String titulo;
    private String cuerpo;
    private int juegoId;
    private Juego juego;
    private int creadorId;
    private Creador creador;

    public Noticia(int id, String fecha, String titulo, String cuerpo, int juegoId, Juego juego, int creadorId, Creador creador) {
        this.id = id;
        this.fecha = fecha;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.juegoId = juegoId;
        this.juego = juego;
        this.creadorId = creadorId;
        this.creador = creador;
    }

    public Noticia(String fecha, String titulo, String cuerpo, int juegoId, Juego juego, int creadorId, Creador creador) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.juegoId = juegoId;
        this.juego = juego;
        this.creadorId = creadorId;
        this.creador = creador;
    }

    public Noticia(int id, String fecha, String titulo, String cuerpo, int juegoId, int creadorId) {
        this.id = id;
        this.fecha = fecha;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.juegoId = juegoId;
        this.creadorId = creadorId;
    }

    public Noticia() {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Creador getCreador() {
        return creador;
    }

    public void setCreador(Creador creador) {
        this.creador = creador;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public int getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(int creadorId) {
        this.creadorId = creadorId;
    }
}
