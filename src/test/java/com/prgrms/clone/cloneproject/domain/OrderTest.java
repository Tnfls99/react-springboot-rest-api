package com.prgrms.clone.cloneproject.domain;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.util.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    @Test
    @DisplayName("상품 없이 주문 생성이 불가능합니다.")
    void testNotCreateOrderWithoutProduct() {
        assertThatThrownBy(() -> {
            new Order(1, new ArrayList<>(), OrderStatus.READY_FOR_DELIVERY);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}