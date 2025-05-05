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
            name = "order_products", // Alterado de order_dishes para order_products
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id") // Alterado de dish_id para product_id
    )
    private List<Product> products;
}
