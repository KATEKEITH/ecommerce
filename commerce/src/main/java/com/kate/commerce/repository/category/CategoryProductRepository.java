package com.kate.commerce.repository.category;

import com.kate.commerce.domain.product.CategoryProduct;
import com.kate.commerce.dto.product.CategoryProductDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
        @Query(value = "SELECT new com.kate.commerce.controller.dto.product.CategoryProductDTO(cp.categoryProductId, cp.categoryId, cp.productId, p.productName, p.imageUrl, p.price) "
                        +
                        "FROM CategoryProduct cp " +
                        "INNER JOIN Product p ON cp.productId = p.id " +
                        "WHERE cp.isDeleted = false AND cp.isExposed = true " +
                        "ORDER BY cp.updatedAt DESC ")
        List<CategoryProductDTO> findAllByCategoryIdAndIsDeletedNot(Long categoryId,
                        Pageable pageable);
}
