package com.novi.techiteasy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "wall_bracket")
public class WallBracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private Boolean ajustable;
    private String name;
    private Double price;

    @ManyToMany(mappedBy = "wallBrackets")
    private List<Television> televisions;
}
