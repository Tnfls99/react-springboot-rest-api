package com.prgrms.clone.cloneproject.order.controller;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderDTO;
import com.prgrms.clone.cloneproject.order.service.OrderProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/shop")
public class OrderController {

    private final OrderProvider orderProvider;

    public OrderController(OrderProvider orderProvider) {
        this.orderProvider = orderProvider;
    }

    @PostMapping("/orders/new")
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO, UriComponentsBuilder uriComponentsBuilder) {
        Order newOrder = orderProvider.createNewOrder(orderDTO);

        URI location = uriComponentsBuilder.path("/shop/orders/{orderId}")
                .buildAndExpand(newOrder.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("주문을 성공하였습니다.");
    }

    @GetMapping("orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable Integer orderId) {
        Order order = orderProvider.findOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
