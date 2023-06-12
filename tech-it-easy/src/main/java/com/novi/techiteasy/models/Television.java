package com.novi.techiteasy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@JsonIgnoreProperties("{brand}")
@Table(name = "television")
public class Television {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    //@JsonProperty("brand")
    private String brand;

    private String tvName;

    private Double price;

    private Double availableSize;

    private Double refreshRate;

    private String screenType;

    private String screenQuality;

    private Boolean smartTv;

    private Boolean wifi;

    private Boolean voiceControl;

    private Boolean hdr;

    private Boolean bluetooth;

    private Boolean ambiLight;

    private Integer originalStock;

    private Integer sold;

    @OneToOne
    @JsonIgnore
    private Remote remote;

    @OneToMany(mappedBy = "television")
    private List<CiModule> ciModule;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "television_wallbracket",
            joinColumns = @JoinColumn(name = "television_id"),
            inverseJoinColumns = @JoinColumn(name = "wallbracket_id")
    )
    private List<WallBracket> wallBrackets;
}