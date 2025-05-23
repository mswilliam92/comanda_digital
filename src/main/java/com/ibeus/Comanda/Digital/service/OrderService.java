package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.OrderRequest;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderRequest req) {
        List<Product> produtos = productRepository.findAllById(req.getProductIds());
        if (produtos.size() != req.getProductIds().size()) {
            throw new RuntimeException("Um ou mais produtos não foram encontrados");
        }
        Order order = new Order();
        order.setStatus(req.getStatus());
        order.setProducts(produtos);
        return orderRepository.save(order);
    }

    public List<Order> getOrder() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Order updateOrder(OrderRequest req, Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        List<Product> produtos = productRepository.findAllById(req.getProductIds());
        if (produtos.size() != req.getProductIds().size()) {
            throw new RuntimeException("Um ou mais produtos não foram encontrados");
        }
        existing.setStatus(req.getStatus());
        existing.setProducts(produtos);
        return orderRepository.save(existing);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado");
        }
        orderRepository.deleteById(id);
    }
}
