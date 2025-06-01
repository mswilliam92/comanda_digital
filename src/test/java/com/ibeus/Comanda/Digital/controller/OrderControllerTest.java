package com.ibeus.Comanda.Digital.Controller;

import com.ibeus.Comanda.Digital.controller.OrderController;
import com.ibeus.Comanda.Digital.dto.*;
import com.ibeus.Comanda.Digital.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService service;

    @Autowired
    ObjectMapper objectMapper;

    private OrderResponseDTO sampleResp;
    private OrderRequestDTO sampleReq;

    @BeforeEach
    void setup() {
        // Monta DTO de resposta
        sampleResp = new OrderResponseDTO();
        sampleResp.setId(1L);
        sampleResp.setStatus("OK");
        OrderItemDTO item = new OrderItemDTO();
        item.setProductId(2L);
        item.setProductName("Saia");
        item.setProductDescription("Bonita");
        item.setProductPrice(50.0);
        sampleResp.setItems(List.of(item));

        // Monta DTO de request
        sampleReq = new OrderRequestDTO();
        sampleReq.setStatus("OK");
        sampleReq.setProductIds(List.of(2L));
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(service.getAllOrders()).thenReturn(List.of(sampleResp));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].products[0].name").value("Saia"));

        verify(service).getAllOrders();
    }

    @Test
    void getById_shouldReturnDto() throws Exception {
        when(service.getOrderById(1L)).thenReturn(sampleResp);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value(2));

        verify(service).getOrderById(1L);
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        when(service.createOrder(any())).thenReturn(sampleResp);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleReq)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.status").value("OK"));

        verify(service).createOrder(any());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        when(service.updateOrder(eq(1L), any())).thenReturn(sampleResp);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products").isArray());

        verify(service).updateOrder(eq(1L), any());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(service).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());

        verify(service).deleteOrder(1L);
    }
}
