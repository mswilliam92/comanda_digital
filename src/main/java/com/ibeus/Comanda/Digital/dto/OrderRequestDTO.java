package com.ibeus.Comanda.Digital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank
    private String status;

    @NotEmpty
    private List<Long> productIds;
}
