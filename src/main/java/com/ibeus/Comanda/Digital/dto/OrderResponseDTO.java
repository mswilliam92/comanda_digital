package com.ibeus.Comanda.Digital.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private String status;

    @JsonProperty("products")
    private List<OrderItemDTO> items;
}
