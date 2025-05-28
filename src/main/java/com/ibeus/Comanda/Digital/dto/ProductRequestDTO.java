package com.ibeus.Comanda.Digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Double price;
}
