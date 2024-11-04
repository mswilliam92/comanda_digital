package com.ibeus.Comanda.Digital.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDish> orderDishes = new ArrayList<>();

    public Order() {
    }
    public Order(Long orderId) {
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }

    public void addOrderDish(OrderDish orderDish) {
        orderDishes.add(orderDish);
        orderDish.setOrder(this);
    }

    public void updateTotalPrice() {
        double total = orderDishes.stream()
                .mapToDouble(orderDish -> {
                    double price = orderDish.getDish().getPrice();
                    int quantity = orderDish.getQuantity();
                    double itemTotal = price * quantity;
                    System.out.println("Dish ID: " + orderDish.getDish().getId() +
                            ", Price: " + price +
                            ", Quantity: " + quantity +
                            ", Item Total: " + itemTotal);
                    return itemTotal;
                })
                .sum();
        this.totalPrice = total;
        System.out.println("Total Price Updated: " + total);
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }
}
