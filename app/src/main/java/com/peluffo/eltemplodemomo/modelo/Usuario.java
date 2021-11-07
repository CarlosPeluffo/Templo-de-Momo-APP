package com.peluffo.eltemplodemomo.modelo;

public class Usuario {
    private int id;
    private String nickName;
    private String avatar;

    public Usuario() {
    }

    public Usuario(int id, String nickName, String avatar) {
        this.id = id;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public Usuario(String nickName, String avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
