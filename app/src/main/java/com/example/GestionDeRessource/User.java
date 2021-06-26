package com.example.GestionDeRessource;

import java.io.Serializable;

public class User implements Serializable {

    private String userid;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String password;
    private int type;



    public User(String nom, String prenom, String email, String phone, String password,int type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.type = type;
    }

    public User(String userid ,String nom, String prenom, String email, String phone, String password,int type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userid = userid;
        this.type =type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getnom() {
        return nom;
    }

    public void setnom(String fullname) {
        this.nom = fullname;
    }

    public String getprenom() {
        return prenom;
    }

    public void setprenom(String username) {
        this.prenom = username;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
