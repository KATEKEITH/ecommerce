package com.kate.commerce.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.repository.cart.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartQueryService {

    private final CartItemRepository cartItemRepository;

    public List<CartItemProduct> findAllCartItems(List<Long> cartItemIds) {
        return cartItemRepository.findAllCartItems(cartItemIds);
    }

}
