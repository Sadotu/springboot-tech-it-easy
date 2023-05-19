package com.novi.techiteasy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "remote")
public class Remote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String compatibleWith;
    private String batteryType;
    private String remoteName;
    private String brand;
    private double price;
    private Integer originalStock;

    @OneToOne(mappedBy = "remote")
    private Television television;
}
