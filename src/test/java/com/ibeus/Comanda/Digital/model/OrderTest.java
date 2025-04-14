package com.ibeus.Comanda.Digital.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderTest {

    @Test
    public void testGettersAndSetters() {
        Order order = new Order();
        order.setId(100L);
        order.setStatus("Delivered");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product1");
        product.setDescription("Description1");
        product.setPrice(10.0);

        List<Product> productList = Collections.singletonList(product);
        order.setProducts(productList);

        assertEquals(100L, order.getId());
        assertEquals("Delivered", order.getStatus());
        assertEquals(productList, order.getProducts());
    }

    @Test
    public void testEqualsAndHashCode() {
        Order order1 = new Order();
        order1.setId(100L);
        order1.setStatus("Delivered");

        Order order2 = new Order();
        order2.setId(100L);
        order2.setStatus("Delivered");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product1");
        product.setDescription("Description1");
        product.setPrice(10.0);

        List<Product> productList = Arrays.asList(product);
        order1.setProducts(productList);
        order2.setProducts(productList);

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    public void testToString() {
        Order order = new Order();
        order.setId(200L);
        order.setStatus("In Progress");

        Product product = new Product();
        product.setId(2L);
        product.setName("Product2");
        product.setDescription("Description2");
        product.setPrice(20.0);

        order.setProducts(Collections.singletonList(product));

        String result = order.toString();
        assertTrue(result.contains("200"));
        assertTrue(result.contains("In Progress"));
        assertTrue(result.contains("Product2"));
    }
}
