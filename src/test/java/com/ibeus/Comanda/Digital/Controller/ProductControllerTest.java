package com.ibeus.Comanda.Digital.Controller;

import com.ibeus.Comanda.Digital.controller.ProductController;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("Produto Teste");
        mockProduct.setDescription("Descrição teste");
        mockProduct.setPrice(10.0);
    }

    @Test
    void getAllProducts_ReturnsList() {
        when(productService.findAll()).thenReturn(Arrays.asList(mockProduct));

        List<Product> result = productController.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Produto Teste", result.get(0).getName());
    }

    @Test
    void getProductById_ReturnsProductInResponseEntity() {
        when(productService.findById(1L)).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Produto Teste", response.getBody().getName());
    }

    @Test
    void createProduct_ReturnsCreatedResponseEntity() {
        when(productService.create(any(Product.class))).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.createProduct(mockProduct);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertTrue(response.getHeaders().getLocation().toString().endsWith("/1"));
    }

    @Test
    void updateProduct_ReturnsOkResponseEntity() {
        when(productService.update(eq(1L), any(Product.class))).thenReturn(mockProduct);

        ResponseEntity<Product> response = productController.updateProduct(1L, mockProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void deleteProduct_ReturnsNoContent() {
        doNothing().when(productService).delete(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}