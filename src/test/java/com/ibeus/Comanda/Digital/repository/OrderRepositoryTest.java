package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.Order;
import com.ibeus.Comanda.Digital.model.OrderItem;
import com.ibeus.Comanda.Digital.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductRepository productRepo;

    @Test
    void saveOrderWithItems_shouldCascadeSaveOrderItems() {
        // cria um produto de catálogo
        Product prod = new Product();
        prod.setName("Saia");
        prod.setDescription("Bonita");
        prod.setPrice(55.0);
        prod = productRepo.save(prod);

        // monta pedido + item snapshot
        Order order = new Order();
        order.setStatus("NOVO");

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProductId(prod.getId());
        item.setProductName(prod.getName());
        item.setProductDescription(prod.getDescription());
        item.setProductPrice(prod.getPrice());
        order.getItems().add(item);

        // salva e verifica cascade
        Order saved = orderRepo.save(order);
        assertNotNull(saved.getId());
        assertFalse(saved.getItems().isEmpty());
        OrderItem savedItem = saved.getItems().get(0);
        assertNotNull(savedItem.getId());
        assertEquals(prod.getId(), savedItem.getProductId());
        assertEquals("Saia", savedItem.getProductName());
    }

    @Test
    void deletingOrder_shouldRemoveOrderItems_too() {
        // cria produto
        Product prod = new Product();
        prod.setName("X");
        prod.setDescription("Y");
        prod.setPrice(10.0);
        prod = productRepo.save(prod);

        // monta pedido + item
        Order ord = new Order();
        ord.setStatus("A");

        OrderItem it = new OrderItem();
        it.setOrder(ord);
        it.setProductId(prod.getId());
        it.setProductName(prod.getName());
        it.setProductDescription(prod.getDescription());
        it.setProductPrice(prod.getPrice());
        ord.getItems().add(it);

        // salva e exclui
        Order saved = orderRepo.save(ord);
        orderRepo.deleteById(saved.getId());

        // confirma remoção
        assertFalse(orderRepo.findById(saved.getId()).isPresent());
    }

    @Test
    void findAllOrders_shouldIncludePersisted() {
        Order o1 = new Order();
        o1.setStatus("S1");
        Order o2 = new Order();
        o2.setStatus("S2");
        orderRepo.saveAll(List.of(o1, o2));

        List<Order> todos = orderRepo.findAll();
        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(o -> "S1".equals(o.getStatus())));
        assertTrue(todos.stream().anyMatch(o -> "S2".equals(o.getStatus())));
    }
}
