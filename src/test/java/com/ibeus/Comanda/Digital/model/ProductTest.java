package com.ibeus.Comanda.Digital.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testGettersAndSetters() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Description");
        product.setPrice(9.99);

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Description", product.getDescription());
        assertEquals(9.99, product.getPrice());
    }

    @Test
    public void testEqualsAndHashCode() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Test");
        product1.setDescription("Desc");
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Test");
        product2.setDescription("Desc");
        product2.setPrice(10.0);

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testToString() {
        Product product = new Product();
        product.setId(42L);
        product.setName("Test");
        product.setDescription("Test description");
        product.setPrice(100.0);

        String result = product.toString();
        assertTrue(result.contains("42"));
        assertTrue(result.contains("Test"));
        assertTrue(result.contains("Test description"));
        assertTrue(result.contains("100.0"));
    }
}
