package com.kate.commerce.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kate.commerce.controller.cart.CartModelAttributeKeys;
import com.kate.commerce.domain.customer.CustomerDetail;
import com.kate.commerce.domain.product.Product;
import com.kate.commerce.dto.product.ProductDTO;
import com.kate.commerce.exception.NotFoundProductException;
import com.kate.commerce.service.cart.CartService;
import com.kate.commerce.service.product.ProductService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductService productService;

    private final CartService cartService;

    @GetMapping(value = "/product/detail")
    public String detail(@AuthenticationPrincipal CustomerDetail customerDetail,
            @RequestParam(name = "productId") Long productId, Model model) {

        log.info("Product detail page, {}", productId);

        model.addAttribute(CartModelAttributeKeys.CART_ITEM_COUNT_KEY, 0);
        if (Objects.nonNull(customerDetail)) {
            int countCartProduct = cartService.countCartProduct(customerDetail.getCustomer().getCustomerId());
            model.addAttribute(CartModelAttributeKeys.CART_ITEM_COUNT_KEY, countCartProduct);
        }

        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundProductException("상품 정보를 찾을 수 없습니다.");
        }
        Product product = optionalProduct.get();

        model.addAttribute("product", ProductDTO.of(product));

        return "/product/detail";
    }
}
