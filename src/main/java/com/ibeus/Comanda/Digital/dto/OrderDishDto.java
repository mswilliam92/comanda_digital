package com.ibeus.Comanda.Digital.dto;

public class OrderDishDto {
    private Long orderId;
    private Long dishId;
    private int quantity;
//    private String dishName;

    public OrderDishDto(){

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    public String getDishName() {
//        return dishName;
//    }
//
//    public void setDishName(String dishName) {
//        this.dishName = dishName;
//    }
}
