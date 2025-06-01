package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.dto.ProductRequestDTO;
import com.ibeus.Comanda.Digital.dto.ProductResponseDTO;
import com.ibeus.Comanda.Digital.model.Product;
import com.ibeus.Comanda.Digital.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product create(ProductRequestDTO req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        return productRepository.save(p);
    }

    public Product update(Long id, ProductRequestDTO req) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        return productRepository.save(p);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
    }

    public void delete(Long id) {
        Product p = findById(id);
        productRepository.delete(p);
    }

    public ProductResponseDTO toResponseDTO(Product p) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        return dto;
    }
}
