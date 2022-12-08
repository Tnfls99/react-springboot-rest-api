package com.prgrms.clone.cloneproject.customer.repository;

import com.prgrms.clone.cloneproject.customer.domain.Cart;
import com.prgrms.clone.cloneproject.customer.domain.CartItem;
import com.prgrms.clone.cloneproject.customer.repository.util.CartSql;
import com.prgrms.clone.cloneproject.customer.repository.util.CustomerSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JdbcCartRepository implements CartRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCartRepository.class);
    private static final Integer UPDATE_SUCCESS = 1;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GeneratedKeyHolder generatedKeyHolder;

    public JdbcCartRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = new GeneratedKeyHolder();
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
        Integer quantity = resultSet.getInt("quantity");

        return new CartItem(id, cartId, productId, quantity);
    };

    private static SqlParameterSource createSqlParaMap(CartItem cartItem){
        return new MapSqlParameterSource()
                .addValue("cart_id", cartItem.getCartId())
                .addValue("product_id", cartItem.getProductId())
                .addValue("quantity", cartItem.getQuantity());
    }

    @Override
    public void insertCartItem(CartItem cartItem){
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getInsertQuery(), createSqlParaMap(cartItem), generatedKeyHolder, new String[]{"id"});
        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("내부 문제 발생으로 상품을 등록할 수 없습니다.");
        }
        Integer key = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
        cartItem.setId(key);
    }

    @Override
    public Optional<Cart> findCartById(Integer cartId){
        try{
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            CartSql.getFindCartQuery(),
                            Collections.singletonMap("id", cartId),
                            cartRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException emptyResultDataAccessException){
            logger.info(
                    MessageFormat.format("해당 Id를 가진 장바구니가 없습니다. id : [{0}]", cartId));
        }
        return Optional.empty();
    }

    private List<CartItem> findCartItem(Integer cartId) {
        return namedParameterJdbcTemplate.query(
                CartSql.getFindCartItemsQuery(),
                Collections.singletonMap("cart_id", cartId),
                cartItemRowMapper
        );
    }
}
