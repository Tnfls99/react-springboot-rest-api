package com.prgrms.clone.cloneproject.order.controller;

import com.prgrms.clone.cloneproject.customer.domain.dto.CartDTO;
import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderPostDTO;
import com.prgrms.clone.cloneproject.order.service.OrderProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static com.prgrms.clone.cloneproject.customer.service.CustomerProvider.isValidCustomer;

@RestController
@RequestMapping("/shop")
public class OrderController {

    private final OrderProvider orderProvider;

    public OrderController(OrderProvider orderProvider) {
        this.orderProvider = orderProvider;
    }

    @PostMapping("/orders/new")
    public ResponseEntity createOrder(@RequestBody OrderPostDTO orderPostDTO,
                                      UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request) {

        Integer customerId = request.getIntHeader("customerId");

        isValidCustomer(customerId);

        Order newOrder = orderProvider.createNewOrder(orderPostDTO, customerId);

        URI location = uriComponentsBuilder.path("/shop/orders/{orderId}")
                .buildAndExpand(newOrder.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("주문을 성공하였습니다.");
    }

    @PostMapping("/customers/{customerId}/cart/order")
    public ResponseEntity orderByCart(@RequestBody CartDTO cartDTO, @PathVariable Integer customerId,
                                      UriComponentsBuilder uriComponentsBuilder){

        Order order = orderProvider.createNewOrderByCart(cartDTO, customerId);

        URI location = uriComponentsBuilder.path("/shop/orders/{orderId}")
                .buildAndExpand(order.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("주문을 생성합니다.");
    }



    @GetMapping("/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable Integer orderId, HttpServletRequest request) {
        Integer customerId = request.getIntHeader("customerId");

        isValidCustomer(customerId);

        Order order = orderProvider.findOrder(orderId, customerId);

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
