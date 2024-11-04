package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.OrderDishDto;
import com.ibeus.Comanda.Digital.dto.OrderDishRequestDto;
import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderDish;
import com.ibeus.Comanda.Digital.repository.OrderDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDishService {
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;

    public OrderDish saveOrderDish(OrderDish orderDish) {
        return orderDishRepository.save(orderDish);
    }

    public OrderDish findById(Long id) {
        return orderDishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDish não encontrado:" + id));
    }
    public List<OrderDish> findAll() {
        return orderDishRepository.findAll();
    }

    public void deleteById(Long id) {
        orderDishRepository.deleteById(id);
    }

    public OrderDish saveOrderDish(Long id, OrderDishDto orderDishDto) {
        OrderDish orderDish = orderDishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDish não encontrado: " + id));


        Order order = orderService.findById(orderDishDto.getOrderId());
        orderDish.setOrder(order);


        Dish dish = dishService.findById(orderDishDto.getDishId());
        orderDish.setDish(dish);
        orderDish.setQuantity(orderDishDto.getQuantity());

        return orderDishRepository.save(orderDish);
    }
    @Transactional
    public OrderDish addDishToOrder(OrderDishDto orderDishDto) {
        Order order = orderService.findById(orderDishDto.getOrderId());
        Dish dish = dishService.findById(orderDishDto.getDishId());

        OrderDish orderDish = new OrderDish();
        orderDish.setDish(dish);
        orderDish.setQuantity(orderDishDto.getQuantity());
        orderDish.setOrder(order);

        orderDishRepository.save(orderDish);

        updateOrderTotalPrice(order);

        return orderDish;
    }

    private void updateOrderTotalPrice(Order order) {
        double total = order.getOrderDishes().stream()
                .mapToDouble(od -> od.getDish().getPrice() * od.getQuantity())
                .sum();
        order.setTotalPrice(total);
        orderService.saveOrder(order);
    }
}
