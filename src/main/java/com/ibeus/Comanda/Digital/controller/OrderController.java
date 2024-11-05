package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.dto.OrderDishDto;
import com.ibeus.Comanda.Digital.dto.OrderDto;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        Order order = new Order(1L);
        order.setStatus(orderDto.getStatus());
        order.setTotalPrice(orderDto.getTotalPrice());

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(201).body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(orderDto.getStatus());
            order.setTotalPrice(orderDto.getTotalPrice());
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrderByStatus(@PathVariable String status) {
        List<Order> orders = orderService.findOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }



    @GetMapping("/{id}/status")
    public ResponseEntity<String> getOrderStatusById(@PathVariable("id") Long id) {
        try {
        String status = orderService.findOrderStatusById(id);
        return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
