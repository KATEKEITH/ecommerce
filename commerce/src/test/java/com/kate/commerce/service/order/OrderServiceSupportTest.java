package com.kate.commerce.service.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kate.commerce.domain.cart.CartItemProduct;
import com.kate.commerce.domain.customer.Customer;
import com.kate.commerce.domain.order.Order;
import com.kate.commerce.enums.DeliveryType;
import com.kate.commerce.enums.PayType;
import com.kate.commerce.repository.order.OrderItemRepository;
import com.kate.commerce.repository.order.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceSupportTest {

        @Mock
        private OrderRepository orderRepository;

        @Mock
        private OrderItemRepository orderItemRepository;

        @InjectMocks
        private OrderServiceSupport orderServiceSupport;

        @Test
        @DisplayName("쿠폰 적용 없는 주문 생성 -> 상품 재고가 없어 예외 발생")
        void order() {
                // given
                // 고객
                Customer customer = new Customer();
                customer.setCustomerId(1L);
                customer.setAddress("서울시 동일로 109길");

                // 상품
                List<CartItemProduct> targetCartItems = new ArrayList<>();
                CartItemProduct cartItemProduct = new CartItemProduct();
                cartItemProduct.setCartId(1L);
                cartItemProduct.setCartItemId(2L);
                cartItemProduct.setProductId(1L);
                cartItemProduct.setProductName("소쿠리");
                cartItemProduct.setProductPrice(BigDecimal.valueOf(1000));
                cartItemProduct.setProductImageUrl(
                                "https://www.coupang.com/vp/products/2039938005?itemId=3468260640&vendorItemId=71454612737");
                cartItemProduct.setQuantity(5);
                cartItemProduct.setStockQuantity(1);
                cartItemProduct.setDeliveryType(DeliveryType.FREE);
                targetCartItems.add(cartItemProduct);

                // when
                Order order = orderServiceSupport.order(
                                customer,
                                targetCartItems,
                                PayType.CASH,
                                null);

                // then
                // 상품 재고가 있는지 없는 경우 예외 처리
                Assertions.assertThatThrownBy(() -> orderServiceSupport.order(customer,
                                targetCartItems,
                                PayType.CASH,
                                null))
                                .isInstanceOf(
                                                RuntimeException.class)
                                .hasMessageContaining("상품의 재고가 부족합니다.");

                // 쿠폰 정보 조회하여 적용 가능한 상품 인지 확인 만료된 쿠폰인 경우 예외 처리

                // 주문 생성
                Assertions.assertThat(order).isNotNull();

        }

}
