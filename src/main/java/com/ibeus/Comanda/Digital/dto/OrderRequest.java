package com.ibeus.Comanda.Digital.dto;

import java.util.List;

public class OrderRequest {
    private String status;
    private List<Long> productIds;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
