package com.prgrms.clone.cloneproject.order.domain.util;

import java.util.Arrays;
import java.util.Objects;

public enum OrderStatus {
    BEFORE_DEPOSIT("입금전"),
    READY_FOR_DELIVERY("배송준비중"),
    DELIVERING("배송중"),
    DELIVERED("배송완료"),
    CANCELED("취소됨");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static OrderStatus findOrderStatus(String orderStatus) {
        return Arrays.stream(OrderStatus.values())
                .filter(status ->
                        Objects.equals(status.getStatus(), orderStatus))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("상태값을 정의할 수 없습니다."));
    }

    public String getStatus() {
        return status;
    }
}
