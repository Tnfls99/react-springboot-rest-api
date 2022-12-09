package com.prgrms.clone.cloneproject.order.service;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderDTO;
import com.prgrms.clone.cloneproject.order.domain.OrderItem;
import com.prgrms.clone.cloneproject.order.domain.OrderPostDTO;
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

    public Order createNewOrder(OrderPostDTO orderPostDTO, Integer customerId) {
        Integer productId = orderPostDTO.getProductId();
        Integer price = orderPostDTO.getPrice();
        Integer quantity = orderPostDTO.getQuantity();

        OrderItem newOrderItem = new OrderItem(productId, price, quantity);

        Order newOrder = new Order(customerId,
                Collections.singletonList(newOrderItem),
                OrderStatus.BEFORE_DEPOSIT);

        orderRepository.insert(newOrder);

        return newOrder;
    }

    public Order findOrder(Integer orderId, Integer customerId) {
        if (orderRepository.findById(orderId)
                .isEmpty()) {
            throw new NoOrderException("order 데이터가 없어 불러올 수 없습니다.");
        }

        Order order = orderRepository.findById(orderId).get();

        if (Objects.equals(order.getCustomerId(), customerId)) {
            throw new NoAuthenticatedException("고객 정보 없이는 주문 정보를 불러올 수 없습니다.");
        }

        return order;
    }


    public Order createNewOrderByCart(CartDTO cart, Integer customerId) {
        List<CartItemDTO> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("상품 없이 주문이 불가능합니다.");
        }


}
