//package com.ibeus.Comanda.Digital.model;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.*;
//
//import java.util.Collections;
//import java.util.List;
//
//public class OrderRequest {
//
//    private Order order;
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderDish> dishes;
//
//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
//
//    public List<Dish> getDishes() {
//        return Collections.singletonList((Dish) dishes);
//    }
//
//    public void setDishes(List<OrderDish> dishes) {
//        this.dishes = dishes;
//    }
//}
