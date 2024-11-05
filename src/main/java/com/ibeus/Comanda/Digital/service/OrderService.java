package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderStatusHistory;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.OrderStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

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
            String previousStatus = order.getStatus();
            order.setStatus(updatedOrder.getStatus());
            order.setTotalPrice(updatedOrder.getTotalPrice());
            Order savedOrder = orderRepository.save(order);

            if (!previousStatus.equals(updatedOrder.getStatus())) {
                orderStatusHistoryService.addStatusHistory(savedOrder, updatedOrder.getStatus());
            }

            return savedOrder;
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }
    @Transactional
    public void deleteOrder(Long orderId) {
        orderStatusHistoryService.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    public List<Order> findOrdersByStatus (String status) {
        return orderRepository.findByStatus(status);
    }


    public String findOrderStatusById (Long id) {
        return orderRepository.findById(id)
                .map(Order::getStatus)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o ID: " + id));
    }

    public Order updateOrderStatus(Long id, String newStatus) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(newStatus);

            OrderStatusHistory statusHistory = new OrderStatusHistory();
            statusHistory.setOrder(order);
            statusHistory.setStatus(newStatus);
            statusHistory.setTimestamp(LocalDateTime.now());

            orderStatusHistoryRepository.save(statusHistory);

            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
    }

}
