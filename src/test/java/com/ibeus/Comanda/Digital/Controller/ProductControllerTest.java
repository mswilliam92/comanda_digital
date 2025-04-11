package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void getProductById_ReturnsProduct() {
        when(productService.findById(1L)).thenReturn(mockProduct);

        Product result = productController.getProductById(1L);

        assertEquals("Produto Teste", result.getName());
    }

    @Test
    void createProduct_ReturnsCreatedProduct() {
        when(productService.create(any(Product.class))).thenReturn(mockProduct);

        Product result = productController.createProduct(mockProduct);

        assertEquals(1L, result.getId());
    }

    @Test
    void updateProduct_ReturnsUpdatedProduct() {
        when(productService.update(eq(1L), any(Product.class))).thenReturn(mockProduct);

        Product result = productController.updateProduct(1L, mockProduct);

        assertEquals(1L, result.getId());
    }

    @Test
    void deleteProduct_ReturnsNoContent() {
        doNothing().when(productService).delete(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
    }
}
