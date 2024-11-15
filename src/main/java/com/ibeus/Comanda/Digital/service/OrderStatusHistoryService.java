package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderStatusHistory;
import com.ibeus.Comanda.Digital.repository.OrderStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderStatusHistoryService {
    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    public OrderStatusHistory saveOrderStatusHistory(OrderStatusHistory orderStatusHistory) {
        return orderStatusHistoryRepository.save(orderStatusHistory);
    }

    public List<OrderStatusHistory> findByOrderId(Long orderId) {
        return orderStatusHistoryRepository.findByOrderId(orderId);
    }

    public List<OrderStatusHistory> findAll() {
        return orderStatusHistoryRepository.findAll();
    }

    public void addStatusHistory(Order order, String status) {
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(status);
        history.setTimestamp(LocalDateTime.now());
        orderStatusHistoryRepository.save(history);
    }
    @Transactional
    public void deleteByOrderId(Long orderId) {
        orderStatusHistoryRepository.deleteByOrderId(orderId);
    }

    public List<OrderStatusHistory> getHistoryByOrderId(Long orderId) {
        return orderStatusHistoryRepository.findByOrderId(orderId);
    }

}
