package com.novi.techiteasy.DTO.Input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WallBracketInputDTO {
    private Long id;
    private String size;
    private Boolean ajustable;
    private String name;
    private Double price;
}
