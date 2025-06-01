package com.ibeus.Comanda.Digital.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderItemDTO {
    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("price")
    private Double productPrice;
}
