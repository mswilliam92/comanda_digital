package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.OrderDishDto;
import com.ibeus.Comanda.Digital.model.OrderDish;
import com.ibeus.Comanda.Digital.repository.OrderDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDishService {
    @Autowired
    private OrderDishRepository orderDishRepository;

    public OrderDish saveOrderDish(OrderDish orderDish) {
        return orderDishRepository.save(orderDish);
    }

    public Optional<OrderDish> findById(Long id) {
        return orderDishRepository.findById(id);
    }

    public void deleteById(Long id) {
        orderDishRepository.deleteById(id);
    }

}