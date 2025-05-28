package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.dto.OrderRequestDTO;
import com.ibeus.Comanda.Digital.dto.OrderResponseDTO;
import com.ibeus.Comanda.Digital.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(
            @Valid @RequestBody OrderRequestDTO req) {
        OrderResponseDTO resp = orderService.createOrder(req);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resp.getId())
                .toUri();
        return ResponseEntity.created(loc).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(
            @Valid @RequestBody OrderRequestDTO req,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.updateOrder(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
