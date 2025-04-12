package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Coca");
        p1.setDescription("Bebida");
        p1.setPrice(5.0);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Hambúrguer");
        p2.setDescription("Comida");
        p2.setPrice(15.0);

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Coca", result.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindById_Success() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Coca");
        p.setDescription("Bebida");
        p.setPrice(5.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        Product result = productService.findById(1L);

        assertNotNull(result);
        assertEquals("Coca", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.findById(1L);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreate() {
        Product p = new Product();
        p.setName("Coca");
        p.setDescription("Bebida");
        p.setPrice(5.0);

        when(productRepository.save(p)).thenReturn(p);

        Product result = productService.create(p);

        assertEquals("Coca", result.getName());
        verify(productRepository, times(1)).save(p);
    }

    @Test
    public void testUpdate() {
        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Coca");
        existing.setDescription("Bebida");
        existing.setPrice(5.0);

        Product updated = new Product();
        updated.setName("Coca-Cola");
        updated.setDescription("Refrigerante");
        updated.setPrice(6.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product result = productService.update(1L, updated);

        assertEquals("Coca-Cola", result.getName());
        assertEquals("Refrigerante", result.getDescription());
        assertEquals(6.0, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    public void testDelete() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Coca");

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        doNothing().when(productRepository).delete(p);

        productService.delete(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(p);
    }
}
