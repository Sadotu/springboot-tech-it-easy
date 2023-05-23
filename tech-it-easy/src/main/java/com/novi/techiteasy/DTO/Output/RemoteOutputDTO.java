package com.novi.techiteasy.DTO.Output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoteOutputDTO {
    private Long id;
    private String compatibleWith;
    private String batteryType;
    private String remoteName;
    private String brand;
    private double price;
    private Integer originalStrock;
}
