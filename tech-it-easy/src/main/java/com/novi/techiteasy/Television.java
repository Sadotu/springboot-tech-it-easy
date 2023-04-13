package com.novi.techiteasy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Television {
    @Id
    @GeneratedValue
    public long id;
    public String brand;
    public String model;
    public String type;

    public Television(String brand, String model, String type) {
        this.brand = brand;
        this.model = model;
        this.type = type;
    }

    public Television() { }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
