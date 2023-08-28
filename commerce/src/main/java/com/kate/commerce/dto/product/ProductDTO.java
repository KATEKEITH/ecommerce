package com.kate.commerce.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import com.kate.commerce.domain.product.Product;
import com.kate.commerce.enums.DeliveryType;
import com.kate.commerce.enums.ProductStatus;

@Data
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long vendorId;
    private ProductStatus status;
    private String imageUrl;
    private String imageDetailUrl;

    private DeliveryType deliveryType;
    private String productDesc;

    public static ProductDTO of(Product product) {
        return ProductDTO.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .vendorId(product.getVendorId())
                .status(product.getStatus())
                .imageUrl(product.getImageUrl())
                .imageDetailUrl(product.getImageDetailUrl())
                .productDesc(product.getProductDesc())
                .deliveryType(product.getDeliveryType())
                .build();
    }
}
