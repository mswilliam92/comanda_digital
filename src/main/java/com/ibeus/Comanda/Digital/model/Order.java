package com.ibeus.Comanda.Digital.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

    @Entity
    @Table(name = "orders")
    @Data
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String status;

        @ManyToMany
        @JoinTable(
                name = "order_dishes", // Nome da tabela de junção
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "dish_id")
        )
        private List<Dish> products;



    }