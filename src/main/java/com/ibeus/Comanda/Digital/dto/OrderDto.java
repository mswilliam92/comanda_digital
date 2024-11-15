package com.ibeus.Comanda.Digital.dto;

import java.util.List;

public class OrderDto {
    private String status;
    private double totalPrice;
    private List<OrderDishDto> orderDishes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderDishDto> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDishDto> orderDishes) {
        this.orderDishes = orderDishes;
    }
}

