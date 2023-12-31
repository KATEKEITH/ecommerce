package com.kate.commerce.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kate.commerce.domain.cart.CartItem;
import com.kate.commerce.domain.cart.CartItemProduct;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

        @Query(value = "SELECT new com.kate.commerce.domain.cart.CartItemProduct(ci.cartId, ci.cartItemId, ci.productId, p.productName, p.price, p.imageUrl, p.stockQuantity, ci.quantity, p.deliveryType) "
                        +
                        "FROM CartItem ci " +
                        "INNER JOIN Product p ON ci.productId = p.id " +
                        "WHERE ci.cartItemId IN (:cartItemIds) AND ci.isDeleted = false")
        List<CartItemProduct> findAllCartItems(@Param("cartItemIds") List<Long> cartItemIds);

        @Query(value = "SELECT new com.kate.commerce.domain.cart.CartItemProduct(ci.cartId, ci.cartItemId, ci.productId, p.productName, p.price, p.imageUrl, ci.quantity, p.deliveryType) "
                        +
                        "FROM CartItem ci " +
                        "INNER JOIN Product p ON ci.productId = p.id " +
                        "WHERE ci.cartId = :cartId AND ci.isDeleted = false")
        List<CartItemProduct> findAllCartItems(@Param("cartId") Long cartId);

        @Query(value = "SELECT COUNT(ci) " +
                        "FROM CartItem ci " +
                        "WHERE ci.cartId = :cartId AND ci.isDeleted = false ")
        int countCartProduct(Long cartId);

        List<CartItem> findAllByCartIdAndIsDeletedIsFalse(Long cartId);
}
