package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order){
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getOrder(){
        return orderService.getOrder();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
         orderService.deleteOrder(id);
         return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@RequestBody Order orderAtualizada, @PathVariable Long id){
        return orderService.updateOrder(orderAtualizada, id);
    }

}