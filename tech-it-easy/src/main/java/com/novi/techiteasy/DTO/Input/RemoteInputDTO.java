package com.novi.techiteasy.DTO.Input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoteInputDTO {
    private Long id;
    private String compatibleWith;
    private String batteryType;
    private String remoteName;
    private String brand;
    private double price;
    private Integer originalStrock;
}
