package com.prgrms.clone.cloneproject.domain;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderItem;
import com.prgrms.clone.cloneproject.order.domain.util.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    private String address = "28650, 충청북도 청주시 서원구 창직로 97 (사직동) 푸르지오캐슬, 308호";
    private String email = "test@gmail.com";


    @Test
    @DisplayName("상품 없이 주문 생성이 불가능합니다.")
    void testNotCreateOrderWithoutProduct() {
        assertThatThrownBy(() -> {
            new Order(address, new ArrayList<>(), OrderStatus.READY_FOR_DELIVERY, email);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}