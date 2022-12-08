package com.prgrms.clone.cloneproject.order.repository;

import com.prgrms.clone.cloneproject.order.domain.Order;
import com.prgrms.clone.cloneproject.order.domain.OrderItem;
import com.prgrms.clone.cloneproject.order.domain.util.OrderStatus;
import com.prgrms.clone.cloneproject.order.repository.util.OrderSql;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GeneratedKeyHolder generatedOrderKeyHolder;
    private final GeneratedKeyHolder generatedOrderItemKeyHolder;

    public JdbcOrderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedOrderKeyHolder = new GeneratedKeyHolder();
        this.generatedOrderItemKeyHolder = new GeneratedKeyHolder();

    }

    private static Map<String, Object> createOrderParamMap(Order order) {
        return Map.of(
                "id", order.getId(),
                "address", order.getAddress(),
                "orderStatus", order.getOrderStatus()
        );
    }

    private static Map<String, Object> createOrderItemParamMap(Integer orderId, OrderItem orderItem) {
        return Map.of(
                "id", orderItem.getId(),
                "orderId", orderId,
                "productId", orderItem.getProductId(),
                "price", orderItem.getPrice(),
                "quantity", orderItem.getQuantity()

        );
    }

    private static SqlParameterSource createOrderSqlParaMap(Order order) {
        return new MapSqlParameterSource()
                .addValue("address", order.getAddress())
                .addValue("email", order.getEmail())
                .addValue("orderStatus", order.getOrderStatus().getStatus());

    }

    private static SqlParameterSource createOrderItemSqlParaMap(Integer orderId, OrderItem orderItem) {
        return new MapSqlParameterSource()
                .addValue("orderId", orderId)
                .addValue("productId", orderItem.getProductId())
                .addValue("price", orderItem.getPrice())
                .addValue("quantity", orderItem.getQuantity());

    }

    private final RowMapper<Order> OrderRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        String orderStatus = resultSet.getString("order_status");

        OrderStatus status = OrderStatus.findOrderStatus(orderStatus);

        List<OrderItem> orderItems = findOrderItems(id);

        return new Order(id, email, address, orderItems, status);
    };

    private final RowMapper<OrderItem> OrderItemRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        Integer productId = resultSet.getInt("product_id");
        Integer price = resultSet.getInt("price");
        Integer quantity = resultSet.getInt("quantity");

        return new OrderItem(id, productId, price, quantity);
    };

    @Override
    public void insert(Order order) {
        int update = namedParameterJdbcTemplate.update(
                OrderSql.getOrderInsertQuery(),
                createOrderSqlParaMap(order), generatedOrderKeyHolder, new String[]{"id"});
        order.setId(
                Objects.requireNonNull(
                                generatedOrderKeyHolder.getKey())
                        .intValue());

        order.getOrderItems()
                .forEach(orderItem -> {
                    namedParameterJdbcTemplate.update(
                            OrderSql.getOrderItemInsertQuery(),
                            createOrderItemSqlParaMap(order.getId(), orderItem), generatedOrderItemKeyHolder, new String[]{"id"});
                    orderItem.setId(
                            Objects.requireNonNull(
                                            generatedOrderItemKeyHolder.getKey())
                                    .intValue());
                });

    }

    @Override
    public Optional<Order> findById(Integer orderId) {
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                OrderSql.getOrderFindOneQuery(),
                Collections.singletonMap("id", orderId), OrderRowMapper
        ));
    }

    private List<OrderItem> findOrderItems(Integer orderItemId) {
        return namedParameterJdbcTemplate.query(OrderSql.getOrderItems(),
                Collections.singletonMap("id", orderItemId),
                OrderItemRowMapper);
    }
}
