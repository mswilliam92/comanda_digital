package com.ibeus.Comanda.Digital.Controller;

import com.ibeus.Comanda.Digital.controller.ProductController;
import com.ibeus.Comanda.Digital.dto.ProductRequestDTO;
import com.ibeus.Comanda.Digital.dto.ProductResponseDTO;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductResponseDTO sampleDto;
    private Product sampleEntity;

    @BeforeEach
    void setup() {
        // DTO que esperamos no JSON de resposta
        sampleDto = new ProductResponseDTO();
        sampleDto.setId(1L);
        sampleDto.setName("Saia Branca");
        sampleDto.setDescription("Bonita");
        sampleDto.setPrice(50.0);

        // Entidade que o service vai retornar
        sampleEntity = new Product();
        sampleEntity.setId(1L);
        sampleEntity.setName("Saia Branca");
        sampleEntity.setDescription("Bonita");
        sampleEntity.setPrice(50.0);
    }

    @Test
    void GET_products_shouldReturnList() throws Exception {
        // agora há um elemento na lista
        when(service.findAll()).thenReturn(List.of(sampleEntity));
        when(service.toResponseDTO(eq(sampleEntity))).thenReturn(sampleDto);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Saia Branca"));

        verify(service).findAll();
    }

    @Test
    void GET_productById_shouldReturnDto() throws Exception {
        when(service.findById(1L)).thenReturn(sampleEntity);
        when(service.toResponseDTO(eq(sampleEntity))).thenReturn(sampleDto);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.price").value(50.0));

        verify(service).findById(1L);
    }

    @Test
    void POST_products_shouldCreateAndReturnCreated() throws Exception {
        ProductRequestDTO req = new ProductRequestDTO();
        req.setName("Saia Branca");
        req.setDescription("Bonita");
        req.setPrice(50.0);

        when(service.create(any(ProductRequestDTO.class))).thenReturn(sampleEntity);
        when(service.toResponseDTO(eq(sampleEntity))).thenReturn(sampleDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/products/1")))
                .andExpect(jsonPath("$.name").value("Saia Branca"));

        verify(service).create(any(ProductRequestDTO.class));
    }

    @Test
    void PUT_products_shouldUpdateAndReturnOk() throws Exception {
        ProductRequestDTO req = new ProductRequestDTO();
        req.setName("Saia Rosa");
        req.setDescription("Linda");
        req.setPrice(55.0);

        when(service.update(eq(1L), any(ProductRequestDTO.class))).thenReturn(sampleEntity);
        when(service.toResponseDTO(eq(sampleEntity))).thenReturn(sampleDto);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).update(eq(1L), any(ProductRequestDTO.class));
    }

    @Test
    void DELETE_products_shouldReturnNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}
