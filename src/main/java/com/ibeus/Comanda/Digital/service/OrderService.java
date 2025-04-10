package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
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
    private ProductRepository productRepository;

    public Order createOrder(Order order){
        List<Product> produtoSalvo = new ArrayList<>();
        for (Product product : order.getProducts()){
            Product produtoExistente = productRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            produtoSalvo.add(produtoExistente);
        }
        order.setProducts(produtoSalvo);
        try {
            return orderRepository.save(order);
        } catch(Exception e) {
            throw e;
        }
    }

    public List<Order> getOrder(){
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        orderRepository.delete(order);
    }

    public Order updateOrder(Order orderAtualizada, Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        order.setStatus(orderAtualizada.getStatus());
        return orderRepository.save(order);
    }
}
