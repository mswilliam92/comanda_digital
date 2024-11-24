package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.repository.DishRepository;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    public Order createOrder(Order order){
        List<Dish> pratoSalvo = new ArrayList<>();
        for (Dish dish: order.getProducts()){
            Dish pratoExistente = dishRepository.findById(dish.getId()).orElseThrow(() -> new RuntimeException("Prato não encontrado"));
            pratoSalvo.add(pratoExistente);
        }
        order.setProducts(pratoSalvo);
        return orderRepository.save(order);
    }

    public List<Order> getOrder(){
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        orderRepository.delete(order);
    }

    public Order updateOrder(Order orderAtualizada, Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        order.setStatus(orderAtualizada.getStatus());
        return orderRepository.save(order);
    }
}


