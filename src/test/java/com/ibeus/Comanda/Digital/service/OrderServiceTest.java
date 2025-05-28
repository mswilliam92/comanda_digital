package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.OrderRequestDTO;
import com.ibeus.Comanda.Digital.dto.OrderResponseDTO;
import com.ibeus.Comanda.Digital.dto.OrderItemDTO;
import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderItem;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock private OrderRepository orderRepo;
    @Mock private ProductRepository productRepo;
    @InjectMocks private OrderService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_validProducts_shouldSnapshotItems() {
        OrderRequestDTO req = new OrderRequestDTO();
        req.setStatus("OK");
        req.setProductIds(List.of(1L, 2L));

        Product p1 = new Product(); p1.setId(1L); p1.setName("A"); p1.setDescription("D"); p1.setPrice(5.0);
        Product p2 = new Product(); p2.setId(2L); p2.setName("B"); p2.setDescription("D2"); p2.setPrice(7.0);

        when(productRepo.findById(1L)).thenReturn(Optional.of(p1));
        when(productRepo.findById(2L)).thenReturn(Optional.of(p2));
        when(orderRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        OrderResponseDTO resp = service.createOrder(req);

        assertEquals("OK", resp.getStatus());
        assertEquals(2, resp.getItems().size());
        assertEquals(5.0, resp.getItems().get(0).getProductPrice());
    }

    @Test
    void updateOrder_shouldOnlyChangeStatus() {
        Order existing = new Order();
        existing.setId(1L);
        existing.setStatus("OLD");
        OrderItem item = new OrderItem();
        item.setOrder(existing);
        item.setProductId(10L);
        item.setProductName("X");
        item.setProductPrice(20.0);
        existing.getItems().add(item);

        when(orderRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(orderRepo.save(existing)).thenReturn(existing);

        OrderRequestDTO req = new OrderRequestDTO();
        req.setStatus("NEW");
        req.setProductIds(List.of()); // não mexe nos itens

        OrderResponseDTO resp = service.updateOrder(1L, req);

        assertEquals("NEW", resp.getStatus());
        assertEquals(1, resp.getItems().size());
    }

    @Test
    void createOrder_missingProduct_shouldThrow() {
        when(productRepo.findById(5L)).thenReturn(Optional.empty());
        OrderRequestDTO req = new OrderRequestDTO();
        req.setStatus("OK");
        req.setProductIds(List.of(5L));

        assertThrows(RuntimeException.class, () -> service.createOrder(req));
    }

    @Test
    void getAllOrders_shouldReturnMappedList() {
        Order order = new Order();
        order.setId(9L);
        order.setStatus("S1");
        OrderItem it = new OrderItem();
        it.setOrder(order);
        it.setProductId(3L);
        it.setProductName("P");
        it.setProductDescription("D");
        it.setProductPrice(8.0);
        order.getItems().add(it);

        when(orderRepo.findAll()).thenReturn(List.of(order));

        List<OrderResponseDTO> list = service.getAllOrders();
        assertEquals(1, list.size());
        OrderResponseDTO dto = list.get(0);
        assertEquals(9L, dto.getId());
        assertEquals("S1", dto.getStatus());
        OrderItemDTO itemDto = dto.getItems().get(0);
        assertEquals(3L, itemDto.getProductId());
    }

    @Test
    void getOrderById_existing_shouldReturnDto() {
        Order order = new Order();
        order.setId(4L);
        order.setStatus("OK");
        order.setItems(List.of());

        when(orderRepo.findById(4L)).thenReturn(Optional.of(order));

        OrderResponseDTO dto = service.getOrderById(4L);
        assertEquals(4L, dto.getId());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void getOrderById_nonexistent_shouldThrow() {
        when(orderRepo.findById(7L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getOrderById(7L));
    }

    @Test
    void deleteOrder_existing_shouldDelete() {
        when(orderRepo.existsById(2L)).thenReturn(true);
        service.deleteOrder(2L);
        verify(orderRepo).deleteById(2L);
    }

    @Test
    void deleteOrder_nonexistent_shouldThrow() {
        when(orderRepo.existsById(5L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> service.deleteOrder(5L));
    }
}
