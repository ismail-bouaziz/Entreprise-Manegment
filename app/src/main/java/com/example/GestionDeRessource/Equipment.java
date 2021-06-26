package com.example.GestionDeRessource;

import java.io.Serializable;

public class Equipment implements Serializable {
    private String reference;

    private String name;
    private int quantity;


    public Equipment(String reference, String name, int quantity) {
        this.reference = reference;
        this.name = name;
        this.quantity = quantity;

    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
