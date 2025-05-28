package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.*;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderItem;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setStatus(dto.getStatus());

        List<OrderItem> items = dto.getProductIds().stream().map(pid -> {
            Product p = productRepo.findById(pid)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + pid));
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(p.getId());
            item.setProductName(p.getName());
            item.setProductDescription(p.getDescription());
            item.setProductPrice(p.getPrice());
            return item;
        }).toList();

        order.getItems().addAll(items);
        Order saved = orderRepo.save(order);
        return toResponseDTO(saved);
    }

    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        order.setStatus(dto.getStatus());
        // não mexemos em order.getItems() para manter histórico imutável
        Order updated = orderRepo.save(order);
        return toResponseDTO(updated);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        return toResponseDTO(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepo.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado: " + id);
        }
        orderRepo.deleteById(id);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        OrderResponseDTO resp = new OrderResponseDTO();
        resp.setId(order.getId());
        resp.setStatus(order.getStatus());
        resp.setItems(order.getItems().stream().map(item -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setProductDescription(item.getProductDescription());
            dto.setProductPrice(item.getProductPrice());
            return dto;
        }).toList());
        return resp;
    }
}
