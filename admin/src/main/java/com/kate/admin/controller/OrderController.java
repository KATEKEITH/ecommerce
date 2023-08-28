package com.kate.admin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kate.admin.domain.OrderDetailView;
import com.kate.admin.dto.OrderDTO;
import com.kate.admin.exception.NotFoundOrderException;
import com.kate.admin.service.OrderService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class OrderController {

    public static final String MENU_KEY = "orders";

    private final OrderService orderService;

    @GetMapping({ "/orders/", "/orders" })
    public String index(Model model) {
        List<OrderDetailView> orderDetailViews = orderService.findAllOrderDetailView();
        model.addAttribute("orders", orderDetailViews);
        model.addAttribute("menuId", MENU_KEY);
        return "/orders/orders";
    }

    @GetMapping("/orders/order-detail")
    public String detail(@RequestParam Long orderId, Model model) {
        Optional<OrderDTO> optionalOrderDTO = orderService.findById(orderId);
        OrderDTO orderDTO = optionalOrderDTO.orElseThrow(() -> new NotFoundOrderException("Not found order info"));
        model.addAttribute("order", orderDTO);
        model.addAttribute("menuId", MENU_KEY);
        return "/orders/order-detail";
    }
}
