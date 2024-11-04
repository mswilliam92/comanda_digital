package com.ibeus.Comanda.Digital.dto;

import java.util.List;


public class OrderDishRequestDto {
    private Long orderId;
    private List<OrderDishDto> dishes;

    public OrderDishRequestDto() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderDishDto> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishDto> dishes) {
        this.dishes = dishes;
    }
}
