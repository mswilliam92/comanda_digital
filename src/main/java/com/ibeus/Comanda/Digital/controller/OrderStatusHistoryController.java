package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.model.OrderStatusHistory;
import com.ibeus.Comanda.Digital.service.OrderStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-status-history")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderStatusHistoryController {
    @Autowired
    private OrderStatusHistoryService orderStatusHistoryService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderStatusHistory>> getHistoryByOrderId(@PathVariable Long orderId) {
        List<OrderStatusHistory> statusHistory = orderStatusHistoryService.getHistoryByOrderId(orderId);
        return ResponseEntity.ok(statusHistory);
    }
//    @PostMapping
//    public ResponseEntity<OrderStatusHistory> createOrderStatusHistory(@RequestBody OrderStatusHistory orderStatusHistory) {
//        OrderStatusHistory createdHistory = orderStatusHistoryService.saveOrderStatusHistory(orderStatusHistory);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
//    }
}
