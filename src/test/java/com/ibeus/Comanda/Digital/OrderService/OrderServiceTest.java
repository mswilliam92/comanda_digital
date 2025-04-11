package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldSaveOrderWithValidProducts() {
        Product product = new Product();
        product.setId(1L);

        Order order = new Order();
        order.setProducts(List.of(product));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.createOrder(order);

        assertNotNull(savedOrder);
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrder_shouldThrowException_whenProductNotFound() {
        Product product = new Product();
        product.setId(99L);

        Order order = new Order();
        order.setProducts(List.of(product));

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void getOrder_shouldReturnAllOrders() {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getOrder();

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void deleteOrder_shouldDeleteExistingOrder() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.deleteOrder(1L);

        verify(orderRepository).delete(order);
    }

    @Test
    void deleteOrder_shouldThrowException_whenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
    }

    @Test
    void updateOrder_shouldUpdateStatusOfExistingOrder() {
        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setStatus("em andamento");

        Order updatedOrder = new Order();
        updatedOrder.setStatus("finalizado");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.updateOrder(updatedOrder, 1L);

        assertEquals("finalizado", result.getStatus());
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    void updateOrder_shouldThrowException_whenOrderNotFound() {
        Order updatedOrder = new Order();
        updatedOrder.setStatus("cancelado");

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(updatedOrder, 1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
    }
}
