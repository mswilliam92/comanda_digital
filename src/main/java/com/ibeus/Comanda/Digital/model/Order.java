package com.ibeus.Comanda.Digital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status = "EM PREPARO";
    private Double totalPrice;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;


    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

//    public Long getId(){
//        return id;
//    }

    public void setId(Long id){
        this.id = id;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }



}
