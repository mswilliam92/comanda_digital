package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.dto.ProductRequestDTO;
import com.ibeus.Comanda.Digital.dto.ProductResponseDTO;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return productService.findAll().stream()
                .map(productService::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        Product p = productService.findById(id);
        return ResponseEntity.ok(productService.toResponseDTO(p));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody ProductRequestDTO req) {
        Product saved = productService.create(req);
        URI loc = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(loc).body(productService.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO req) {
        Product updated = productService.update(id, req);
        return ResponseEntity.ok(productService.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
