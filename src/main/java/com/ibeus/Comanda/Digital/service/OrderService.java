package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.repository.DishRepository;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    public Order createOrder(Order order){
//        for (Dish dish: order.getProducts()){
//            dishRepository.save(dish);
//        }
//        dishRepository.save(order.getProducts().get(0));
        Dish dish = new Dish();
        dish.setPrice(10.0);
        dish.setName("BANANA");
        dish.setDescription("BANANA GRANDE");

        dishRepository.save(dish);
        return orderRepository.save(order);
    }
}


