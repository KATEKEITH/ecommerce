package com.kate.commerce.service.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.cart.Cart;
import com.kate.commerce.domain.cart.CartItem;
import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.exception.NotFoundCartException;
import com.kate.commerce.exception.NotFoundCartItemException;
import com.kate.commerce.repository.cart.CartItemRepository;
import com.kate.commerce.repository.cart.CartRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public void add(Long customerId, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findByCustomerIdAndIsDeletedIsFalse(customerId);
        if (optionalCart.isEmpty()) {
            cartRepository.save(Cart.of(customerId));
        } else {
            Cart cart = optionalCart.get();
            CartItem cartItem = CartItem.of(cart.getCartId(), productId);
            this.cartItemRepository.save(cartItem);
        }
    }

    public List<CartItemProduct> getCartItems(Long customerId) {
        Optional<Cart> optionalCart = cartRepository.findByCustomerIdAndIsDeletedIsFalse(customerId);
        if (optionalCart.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        Cart cart = optionalCart.get();
        return cartItemRepository.findAllCartItems(cart.getCartId());
    }

    public Optional<Cart> getCartByCustomerId(Long customerId) {
        Optional<Cart> optionalCart = this.cartRepository.findByCustomerIdAndIsDeletedIsFalse(customerId);
        if (optionalCart.isEmpty()) {
            throw new NotFoundCartException("고객의 장바구니를 찾을 수 없습니다.");
        }

        return optionalCart;
    }

    public Optional<Cart> getCartById(Long cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Cart create(Long customerId) {
        Cart cart = Cart.of(customerId);
        return this.cartRepository.save(cart);
    }

    public CartItem remove(Long customerId, Long cartItemId) {
        validateCartItemOwner(customerId, cartItemId);
        Optional<CartItem> optionalCartItem = getCartItem(cartItemId, "삭제할 장바구니 아이템을 찾을 수 없습니다.");
        CartItem cartItem = optionalCartItem.get();
        cartItem.delete();
        return this.cartItemRepository.save(cartItem);
    }

    public int countCartProduct(Long customerId) {
        Optional<Cart> optionalCart = getCartByCustomerId(customerId);
        if (optionalCart.isEmpty()) {
            return 0;
        }

        Cart cart = optionalCart.get();
        int cartProductCount = this.cartItemRepository.countCartProduct(cart.getCartId());
        log.info(">>> Cart Item Count = {}", cartProductCount);
        return cartProductCount;
    }

    private void validateCartItemOwner(Long customerId, Long cartItemId) {
        Optional<Cart> optionalCart = getCartByCustomerId(customerId);
        Cart cart = optionalCart.get();

        Optional<CartItem> optionalCartItem = getCartItem(cartItemId, ">>> 장바구니 아이템을 찾을 수 없습니다.");
        CartItem cartItem = optionalCartItem.get();
        if (cart.getCartId() != cartItem.getCartId()) {
            throw new IllegalArgumentException(">>> 잘못된 장바구니 접근입니다.");
        }
    }

    private Optional<CartItem> getCartItem(Long cartItemId, String message) {
        Optional<CartItem> optionalCartItem = this.cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isEmpty()) {
            throw new NotFoundCartItemException(message);
        }

        return optionalCartItem;
    }

    public void empty(Long customerId) {
        Optional<Cart> optionalCart = this.cartRepository.findByCustomerIdAndIsDeletedIsFalse(customerId);
        if (optionalCart.isEmpty()) {
            throw new NotFoundCartException("고객의 장바구니 정보를 얻을 수 없습니다. 다시 시도하시기 바랍니다.");
        }

        Cart cart = optionalCart.get();
        List<CartItem> optionalCartItems = this.cartItemRepository.findAllByCartIdAndIsDeletedIsFalse(cart.getCartId());
        optionalCartItems.stream().forEach(cartItem -> {
            cartItem.delete();
        });
    }
}
