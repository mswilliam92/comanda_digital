package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado:" + id));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    @Transactional
    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        orderRepository.flush();
        return savedOrder;
    }
    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(updatedOrder.getStatus());
            order.setTotalPrice(updatedOrder.getTotalPrice());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


}
