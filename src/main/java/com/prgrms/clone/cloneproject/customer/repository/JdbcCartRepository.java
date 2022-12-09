package com.prgrms.clone.cloneproject.customer.repository;

import com.prgrms.clone.cloneproject.customer.domain.Cart;
import com.prgrms.clone.cloneproject.customer.domain.CartItem;
import com.prgrms.clone.cloneproject.customer.repository.util.CartSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;

@Repository
public class JdbcCartRepository implements CartRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCartRepository.class);
    private static final Integer UPDATE_SUCCESS = 1;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GeneratedKeyHolder generatedCartItemKeyHolder;
    private final GeneratedKeyHolder generatedCartKeyHolder;

    public JdbcCartRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedCartItemKeyHolder = new GeneratedKeyHolder();
        generatedCartKeyHolder = new GeneratedKeyHolder();
    }

    private final RowMapper<Cart> cartRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        Integer customerId = resultSet.getInt("customer_id");

        List<CartItem> cartItems = findCartItem(id);

        return new Cart(id, customerId, cartItems);
    };
    private final RowMapper<CartItem> cartItemRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        Integer cartId = resultSet.getInt("cart_id");
        Integer productId = resultSet.getInt("product_id");
        Integer price = resultSet.getInt("price");
        Integer quantity = resultSet.getInt("quantity");

        return new CartItem(id, cartId, productId, price, quantity);
    };

    private static SqlParameterSource createSqlParaMap(CartItem cartItem) {
        return new MapSqlParameterSource()
                .addValue("cart_id", cartItem.getCartId())
                .addValue("product_id", cartItem.getProductId())
                .addValue("price", cartItem.getPrice())
                .addValue("quantity", cartItem.getQuantity());
    }

    private static SqlParameterSource createCartSqlParaMap(Cart cart){
        return new MapSqlParameterSource()
                .addValue("customer_id", cart.getCustomerId());
    }

    private static Map<String, Object> createParaMap(Cart cart){
        return Map.of(
                "id", cart.getId(),
                "customer_id", cart.getCustomerId()
        );
    }

    private static Map<String, Object> createCartItemParaMap(CartItem cartItem){
        return Map.of(
                "id", cartItem.getId(),
                "cart_id", cartItem.getCartId(),
                "product_id", cartItem.getProductId(),
                "price", cartItem.getPrice(),
                "quantity", cartItem.getQuantity()
        );
    }

    @Override
    public void insertCartItem(CartItem cartItem) {
        int update = namedParameterJdbcTemplate.update(
                CartSql.getInsertCartItemQuery(), createSqlParaMap(cartItem), generatedCartItemKeyHolder, new String[]{"id"});
        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("내부 문제 발생으로 상품을 장바구니에 등록할 수 없습니다.");
        }
        Integer key = Objects.requireNonNull(generatedCartItemKeyHolder.getKey()).intValue();
        cartItem.setId(key);
    }

    public void updateCartItem(CartItem cartItem){
        int update = namedParameterJdbcTemplate.update(
                CartSql.getUpdateItemQuery(),
                createCartItemParaMap(cartItem)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalArgumentException("장바구니를 수정할 수 없습니다.");
        }

    }

    @Override
    public Optional<Cart> findCartById(Integer cartId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            CartSql.getFindCartQuery(),
                            Collections.singletonMap("id", cartId),
                            cartRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.info(
                    MessageFormat.format("해당 Id를 가진 장바구니가 없습니다. id : [{0}]", cartId));
        }
        return Optional.empty();
    }

    public Optional<Cart> findCartByCustomerId(Integer customerId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            CartSql.getFindCartByCustomerId(),
                            Collections.singletonMap("customer_id", customerId),
                            cartRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.info(
                    MessageFormat.format("해당 Id와 연결된 장바구니가 없습니다. id : [{0}]", customerId));
        }
        return Optional.empty();
    }

    public void insert(Cart newCart) {
        int update = namedParameterJdbcTemplate.update(
                CartSql.getInsertCartQuery(), createCartSqlParaMap(newCart), generatedCartKeyHolder, new String[]{"id"}
        );
        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("내부 문제 발생으로 상품을 장바구니에 등록할 수 없습니다.");
        }
        Integer key = Objects.requireNonNull(generatedCartKeyHolder.getKey()).intValue();
        newCart.setId(key);
    }

    private List<CartItem> findCartItem(Integer cartId) {
        return namedParameterJdbcTemplate.query(
                CartSql.getFindCartItemsQuery(),
                Collections.singletonMap("cart_id", cartId),
                cartItemRowMapper
        );
    }
}
