package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Adiciono importações explícitas para as interfaces de repositório
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.ProductRepository;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        return product;
    }

    private Order createOrder(Product product) {
        Order order = new Order();
        order.setStatus("PENDING");
        // Use uma lista modificável para evitar UnsupportedOperationException
        order.setProducts(new ArrayList<>(Arrays.asList(product)));
        return order;
    }

    @Test
    public void testSaveOrder() {
        Product product = productRepository.save(createProduct());
        Order order = createOrder(product);
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId(), "O ID do pedido salvo não deve ser nulo");
        assertEquals("PENDING", savedOrder.getStatus());
        assertNotNull(savedOrder.getProducts());
        assertEquals(1, savedOrder.getProducts().size());
    }

    @Test
    public void testFindById() {
        Product product = productRepository.save(createProduct());
        Order order = createOrder(product);
        Order savedOrder = orderRepository.save(order);
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());
        assertTrue(foundOrder.isPresent(), "O pedido deve ser encontrado pelo ID");
        assertEquals("PENDING", foundOrder.get().getStatus());
    }

    @Test
    public void testFindAll() {
        orderRepository.deleteAll();
        productRepository.deleteAll();

        Product product1 = productRepository.save(createProduct());
        orderRepository.save(createOrder(product1));

        Product product2 = productRepository.save(createProduct());
        orderRepository.save(createOrder(product2));

        List<Order> orders = orderRepository.findAll();
        assertEquals(2, orders.size(), "Deve haver 2 pedidos no repositório");
    }

    @Test
    public void testUpdateOrder() {
        Product product = productRepository.save(createProduct());
        Order order = orderRepository.save(createOrder(product));
        order.setStatus("COMPLETED");
        Order updatedOrder = orderRepository.save(order);
        assertEquals("COMPLETED", updatedOrder.getStatus(), "O status do pedido deve ser atualizado para COMPLETED");
    }

    @Test
    public void testDeleteOrder() {
        Product product = productRepository.save(createProduct());
        Order order = orderRepository.save(createOrder(product));
        Long orderId = order.getId();
        orderRepository.delete(order);
        Optional<Order> deletedOrder = orderRepository.findById(orderId);
        assertFalse(deletedOrder.isPresent(), "O pedido deve ser deletado e não encontrado");
    }
}
