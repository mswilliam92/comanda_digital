package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.service.DishService;
import com.ibeus.Comanda.Digital.service.OrderService;
import com.ibeus.Comanda.Digital.service.OrderDishService;
import com.ibeus.Comanda.Digital.dto.OrderDishDto;
import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderDish;
import com.ibeus.Comanda.Digital.repository.OrderDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-dish")
public class OrderDishController {
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;
    @Autowired
    private OrderDishService orderDishService;

    @PostMapping
    public ResponseEntity<OrderDish> addDishToOrder(@RequestBody OrderDishDto orderDishDto) {
        Order order = orderService.findById(orderDishDto.getOrderId());
        Dish dish = dishService.findById(orderDishDto.getDishId());

        OrderDish orderDish = new OrderDish();
        orderDish.setOrder(order);
        orderDish.setDish(dish);
        orderDish.setQuantity(orderDishDto.getQuantity());

        OrderDish savedOrderDish = orderDishService.saveOrderDish(orderDish);
        return ResponseEntity.status(201).body(savedOrderDish);
    }

    @GetMapping
    public List<OrderDish> getAllOrderDishes() {
        return orderDishRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDish> getOrderDishById(@PathVariable Long id) {
        return orderDishRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDish(@PathVariable Long id) {
        if (orderDishRepository.existsById(id)) {
            orderDishRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
