package com.peluffo.eltemplodemomo.modelo;

import java.io.Serializable;

public class Juego implements Serializable {
    private int id;
    private String titulo;
    private String portada;
    private String descripcion;
    private String requisitos;
    private double precio;
    private int creadorId;
    private Creador creador;
    private String portadaMovil;

    public Juego(int id, String titulo, String portada, String descripcion, String requisitos, double precio, int creadorId, Creador creador, String portadaMovil) {
        this.id = id;
        this.titulo = titulo;
        this.portada = portada;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.precio = precio;
        this.creadorId = creadorId;
        this.creador = creador;
        this.portadaMovil = portadaMovil;
    }

    public Juego(String titulo, String portada, String descripcion, String requisitos, double precio, int creadorId, Creador creador, String portadaMovil) {
        this.titulo = titulo;
        this.portada = portada;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.precio = precio;
        this.creadorId = creadorId;
        this.creador = creador;
        this.portadaMovil = portadaMovil;
    }

    public Juego() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(int creadorId) {
        this.creadorId = creadorId;
    }

    public Creador getCreador() {
        return creador;
    }

    public void setCreador(Creador creador) {
        this.creador = creador;
    }

    public String getPortadaMovil() {
        return portadaMovil;
    }

    public void setPortadaMovil(String portadaMovil) {
        this.portadaMovil = portadaMovil;
    }
}
