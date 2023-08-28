package com.kate.commerce.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.kate.commerce.domain.product.Product;
import com.kate.commerce.repository.product.ProductRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }
}
