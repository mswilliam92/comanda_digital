package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.ProductRequestDTO;
import com.ibeus.Comanda.Digital.dto.ProductResponseDTO;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnProduct() {
        ProductRequestDTO req = new ProductRequestDTO();
        req.setName("Teste");
        req.setDescription("Desc");
        req.setPrice(10.0);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Teste");
        saved.setDescription("Desc");
        saved.setPrice(10.0);

        when(productRepo.save(any())).thenReturn(saved);

        Product result = service.create(req);

        assertNotNull(result.getId());
        assertEquals("Teste", result.getName());
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void update_existing_shouldModifyAndReturn() {
        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Old");
        existing.setDescription("OldDesc");
        existing.setPrice(5.0);

        ProductRequestDTO req = new ProductRequestDTO();
        req.setName("New");
        req.setDescription("NewDesc");
        req.setPrice(15.0);

        when(productRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepo.save(existing)).thenReturn(existing);

        Product result = service.update(1L, req);

        assertEquals("New", result.getName());
        assertEquals(15.0, result.getPrice());
        verify(productRepo).findById(1L);
        verify(productRepo).save(existing);
    }

    @Test
    void update_nonexistent_shouldThrow() {
        when(productRepo.findById(99L)).thenReturn(Optional.empty());

        ProductRequestDTO req = new ProductRequestDTO();
        req.setName("X"); req.setDescription("X"); req.setPrice(1.0);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(99L, req));
        assertTrue(ex.getMessage().contains("Produto não encontrado"));
    }

    @Test
    void findAll_shouldReturnList() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("A");
        p1.setDescription("D");
        p1.setPrice(1.0);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("B");
        p2.setDescription("E");
        p2.setPrice(2.0);

        when(productRepo.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(p1, result.get(0));
        verify(productRepo).findAll();
    }

    @Test
    void findById_existing_shouldReturn() {
        Product p = new Product();
        p.setId(3L);
        p.setName("C");
        p.setDescription("F");
        p.setPrice(3.0);

        when(productRepo.findById(3L)).thenReturn(Optional.of(p));

        Product result = service.findById(3L);

        assertEquals(3L, result.getId());
        verify(productRepo).findById(3L);
    }

    @Test
    void findById_nonexistent_shouldThrow() {
        when(productRepo.findById(4L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(4L));
        assertTrue(ex.getMessage().contains("Produto não encontrado"));
    }

    @Test
    void delete_existing_shouldDelete() {
        Product p = new Product();
        p.setId(5L);
        p.setName("X");
        p.setDescription("Y");
        p.setPrice(1.0);

        when(productRepo.findById(5L)).thenReturn(Optional.of(p));

        service.delete(5L);

        verify(productRepo).delete(p);
    }

    @Test
    void delete_nonexistent_shouldThrow() {
        when(productRepo.findById(6L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.delete(6L));
    }

    @Test
    void toResponseDTO_shouldMapFields() {
        Product p = new Product();
        p.setId(7L);
        p.setName("Map");
        p.setDescription("DescMap");
        p.setPrice(9.0);

        ProductResponseDTO dto = service.toResponseDTO(p);

        assertEquals(7L, dto.getId());
        assertEquals("Map", dto.getName());
        assertEquals("DescMap", dto.getDescription());
        assertEquals(9.0, dto.getPrice());
    }
}
