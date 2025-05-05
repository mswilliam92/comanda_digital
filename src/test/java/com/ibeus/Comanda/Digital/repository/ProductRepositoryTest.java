package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product createProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        return product;
    }

    @Test
    public void testSaveProduct() {
        Product product = createProduct();
        Product saved = productRepository.save(product);
        assertNotNull(saved.getId(), "Saved product should have an ID");
        assertEquals("Test Product", saved.getName());
        assertEquals("Test Description", saved.getDescription());
        assertEquals(100.0, saved.getPrice());
    }

    @Test
    public void testFindById() {
        Product product = createProduct();
        Product saved = productRepository.save(product);
        Optional<Product> retrieved = productRepository.findById(saved.getId());
        assertTrue(retrieved.isPresent(), "Product should be found by ID");
        assertEquals("Test Product", retrieved.get().getName());
    }

    @Test
    public void testFindAll() {
        productRepository.deleteAll();
        Product product1 = createProduct();
        product1.setName("Product 1");
        Product product2 = createProduct();
        product2.setName("Product 2");
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size(), "There should be 2 products in the repository");
    }

    @Test
    public void testUpdateProduct() {
        Product product = createProduct();
        Product saved = productRepository.save(product);
        saved.setName("Updated Name");
        Product updated = productRepository.save(saved);
        assertEquals("Updated Name", updated.getName(), "Product name should be updated");
    }

    @Test
    public void testDeleteProduct() {
        Product product = createProduct();
        Product saved = productRepository.save(product);
        Long id = saved.getId();
        productRepository.delete(saved);
        Optional<Product> deleted = productRepository.findById(id);
        assertFalse(deleted.isPresent(), "Product should be deleted");
    }
}
