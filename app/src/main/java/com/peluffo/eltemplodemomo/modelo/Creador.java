package com.peluffo.eltemplodemomo.modelo;

import java.io.Serializable;

public class Creador implements Serializable {
    private int id;
    private String nickName;
    private String mail;
    private String password;
    private String nombre;
    private String apellido;
    private String avatar;

    public Creador(int id, String nickName, String mail, String password, String nombre, String apellido, String avatar) {
        this.id = id;
        this.nickName = nickName;
        this.mail = mail;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.avatar = avatar;
    }

    public Creador(String nickName, String mail, String password, String nombre, String apellido, String avatar) {
        this.nickName = nickName;
        this.mail = mail;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.avatar = avatar;
    }

    public Creador() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
