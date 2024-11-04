package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.dto.OrderDishRequestDto;
import com.ibeus.Comanda.Digital.service.DishService;
import com.ibeus.Comanda.Digital.service.OrderService;
import com.ibeus.Comanda.Digital.service.OrderDishService;
import com.ibeus.Comanda.Digital.dto.OrderDishDto;
import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderDish;
import com.ibeus.Comanda.Digital.repository.OrderDishRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<OrderDish>> addDishesToOrder(@RequestBody OrderDishRequestDto orderDishRequestDto) {
        if (orderDishRequestDto.getOrderId() == null) {
            return ResponseEntity.badRequest().body(null);
        }


        Order order = orderService.findById(orderDishRequestDto.getOrderId());


        List<OrderDish> orderDishes = new ArrayList<>();


        if (orderDishRequestDto.getDishes() == null || orderDishRequestDto.getDishes().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }


        for (OrderDishDto orderDishDto : orderDishRequestDto.getDishes()) {

            if (orderDishDto.getDishId() == null) {
                return ResponseEntity.badRequest().body(null);
            }


            Dish dish = dishService.findById(orderDishDto.getDishId());


            if (dish == null) {
                return ResponseEntity.badRequest().body(null);
            }


            OrderDish orderDish = new OrderDish();
            orderDish.setOrder(order);
            orderDish.setDish(dish);
            orderDish.setQuantity(orderDishDto.getQuantity());


            orderDishes.add(orderDishService.saveOrderDish(orderDish));
        }

        return ResponseEntity.status(201).body(orderDishes);
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
