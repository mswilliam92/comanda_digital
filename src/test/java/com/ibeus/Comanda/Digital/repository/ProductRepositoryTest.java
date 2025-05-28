package com.ibeus.Comanda.Digital.repository;

import com.ibeus.Comanda.Digital.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepo;

    @Test
    void saveAndFindById_shouldPersistAndLoadProduct() {
        // arrange
        Product p = new Product();
        p.setName("Teste");
        p.setDescription("Desc");
        p.setPrice(42.0);

        // act
        Product saved = productRepo.save(p);
        Optional<Product> found = productRepo.findById(saved.getId());

        // assert
        assertTrue(found.isPresent());
        assertEquals("Teste", found.get().getName());
        assertEquals("Desc", found.get().getDescription());
        assertEquals(42.0, found.get().getPrice());
    }

    @Test
    void deleteById_shouldRemoveProduct() {
        // arrange: cria a entidade sem usar double‐brace
        Product p = new Product();
        p.setName("X");
        p.setDescription("Y");
        p.setPrice(1.0);
        p = productRepo.save(p);

        // act
        productRepo.deleteById(p.getId());

        // assert
        assertFalse(productRepo.findById(p.getId()).isPresent());
    }
}
