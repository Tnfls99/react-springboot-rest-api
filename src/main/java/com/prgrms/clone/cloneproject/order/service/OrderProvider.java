package com.prgrms.clone.cloneproject.order.service;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderDTO;
import com.prgrms.clone.cloneproject.order.domain.OrderItem;
import com.prgrms.clone.cloneproject.order.domain.util.OrderStatus;
import com.prgrms.clone.cloneproject.order.repository.JdbcOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProvider {

    private final JdbcOrderRepository orderRepository;

    public OrderProvider(JdbcOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createNewOrder(OrderDTO orderDTO) {
        String address = orderDTO.getAddress();
        List<OrderItem> orderItems = orderDTO.getOrderItems();
        String email = orderDTO.getEmail();

        Order newOrder = new Order(address, orderItems, OrderStatus.BEFORE_DEPOSIT, email);

        orderRepository.insert(newOrder);

        return newOrder;
    }

    public Order findOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order를 가져올 수 없음."));
    }


}
