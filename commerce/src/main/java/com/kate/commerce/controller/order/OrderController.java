package com.kate.commerce.controller.order;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.domain.customer.Customer;
import com.kate.commerce.domain.customer.CustomerDetail;
import com.kate.commerce.dto.order.DirectOrderRequestDTO;
import com.kate.commerce.dto.order.OrderRequestDTO;
import com.kate.commerce.exception.NotFoundCustomerException;
import com.kate.commerce.service.cart.CartService;
import com.kate.commerce.service.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CartService cartService;

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public String order(@AuthenticationPrincipal CustomerDetail customerDetail, OrderRequestDTO orderRequest,
            RedirectAttributes redirectAttributes) {
        log.info(">>> 주문 처리, 고객 정보, {}", customerDetail);

        if (Objects.isNull(customerDetail)) {
            throw new NotFoundCustomerException("고객 정보를 찾을 수 없습니다.");
        }
        Customer customer = customerDetail.getCustomer();

        List<CartItemProduct> cartItems = cartService.getCartItems(customer.getCustomerId());
        List<Long> cartItemIds = cartItems.stream().map(cartItemProduct -> cartItemProduct.getCartItemId())
                .collect(Collectors.toList());
        Long orderId = this.orderService.order(customer, cartItemIds, orderRequest.getPayType());
        cartService.empty(customer.getCustomerId());

        redirectAttributes.addFlashAttribute("orderId", orderId);

        return "redirect:/thanks";
    }

    @PostMapping(value = "/direct-order")
    public String directOrder(@AuthenticationPrincipal CustomerDetail customerDetail,
            DirectOrderRequestDTO directOrderRequestDTO, RedirectAttributes redirectAttributes) {
        log.info(">>> 주문 처리, 고객 정보, {}", customerDetail);

        if (Objects.isNull(customerDetail)) {
            throw new NotFoundCustomerException("고객 정보를 찾을 수 없습니다.");
        }
        Customer customer = customerDetail.getCustomer();

        Long orderId = this.orderService.directOrder(customer, directOrderRequestDTO.getProductId(),
                directOrderRequestDTO.getPayType());
        redirectAttributes.addFlashAttribute("orderId", orderId);

        return "redirect:/thanks";
    }

}
