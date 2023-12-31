package com.kate.commerce.repository.product;

import com.kate.commerce.domain.product.Product;
import com.kate.commerce.dto.search.SearchResultDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIdIn(List<Long> productIds);

    @Query(value = "SELECT new com.kate.commerce.controller.dto.search.SearchResultDTO(p.id, p.productName, p.imageUrl, p.price) "
            +
            "FROM Product p " +
            "WHERE (p.productName LIKE %:keyword% OR p.productDesc LIKE %:keyword%) AND p.isDeleted = false AND p.isExposed = true "
            +
            "ORDER BY p.updatedAt DESC ")
    List<SearchResultDTO> search(@Param(value = "keyword") String keyword);
}
