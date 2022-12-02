package com.prgrms.clone.cloneproject.product.repository;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.util.Category;
import com.prgrms.clone.cloneproject.product.domain.util.Color;
import com.prgrms.clone.cloneproject.product.repository.util.ProductSql;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private static final Integer UPDATE_SUCCESS = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcProductRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GeneratedKeyHolder generatedKeyHolder;

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        Integer price = resultSet.getInt("price");
        String color = resultSet.getString("color");
        Integer stock = resultSet.getInt("stock");
        LocalDateTime registeredAt = resultSet.getTimestamp("registered_at")
                .toLocalDateTime()
                .truncatedTo(
                        ChronoUnit.SECONDS);
        Boolean isMade = resultSet.getBoolean("is_made");
        String imageUrl = resultSet.getString("image_url");

        try {
            return new Product(id, name,
                    Category.valueOf(category), price,
                    Color.valueOf(color), stock, isMade, registeredAt, imageUrl);
        } catch (IllegalArgumentException illegalArgumentException) {
            String errorDetail = MessageFormat.format("결과를 가져올수 없습니다. 값들을 [id: {0}, name: {1}, category: {2}, price: {3}, color: {4}, stock: {5}] 확인해주세요.",
                    id, name, category, price, color, stock);
            throw new IllegalStateException(errorDetail, illegalArgumentException);
        }
    };

    private static Map<String, Object> createParamMap(Product product) {
        return Map.of(
                "id", product.getId(),
                "name", product.getName(),
                "price", product.getPrice(),
                "category", product.getCategory().name(),
                "color", product.getColor().name(),
                "stock", product.getStock(),
                "registered_at", product.getRegisteredAt(),
                "image_url", product.getImageUrl(),
                "is_made", product.getIsMade()
        );
    }

    private static SqlParameterSource createSqlParaMap(Product product) {
        return new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("category", product.getCategory().name())
                .addValue("color", product.getColor().name())
                .addValue("stock", product.getStock())
                .addValue("registered_at", product.getRegisteredAt())
                .addValue("image_url", product.getImageUrl())
                .addValue("is_made", product.getIsMade());

    }

    public JdbcProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = new GeneratedKeyHolder();
    }

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllQuery(), productRowMapper
        );
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(ProductSql.getFindByIdQuery(),
                            Collections.singletonMap("id", productId),
                            productRowMapper));
        } catch (IllegalStateException illegalStateException) {
            logger.error(illegalStateException.getMessage(), illegalStateException);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.info("해당 id를 가진 상품이 없음. [id: {}]", productId);
        }
        return Optional.empty();
    }

    @Override
    public void insert(Product product) {
        int update = namedParameterJdbcTemplate.update(
                ProductSql.getInsertQuery(), createSqlParaMap(product), generatedKeyHolder, new String[]{"id"});
        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("내부 문제 발생으로 상품을 등록할 수 없습니다.");
        }
        Integer key = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
        product.setId(key);
    }

    public void updateName(Product product) {
        int update = namedParameterJdbcTemplate.update(
                ProductSql.getUpdateNameQuery(), createParamMap(product)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException();
        }
    }

    public void deleteById(Integer productId) {
        int update = namedParameterJdbcTemplate.update(
                ProductSql.getDeleteQuery(),
                Collections.singletonMap("id", productId)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("입력받은 id에 해당하는 상품이 없기 때문에 삭제할 수 없습니다.");
        }
    }

    public List<Product> findAllOrderByLowPrice() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllByLowPriceQuery(), productRowMapper);
    }

    public List<Product> findAllOrderByHighPrice() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllByHighPriceQuery(), productRowMapper);
    }

    public List<Product> findAllOrderByName() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllByNameQuery(), productRowMapper);
    }

    public List<Product> findAllOrderByRegisteredAt() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllByRegisteredAtQuery(), productRowMapper);
    }

    public List<Product> findAllMadeProduct() {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindAllMadeProductQuery(), productRowMapper);
    }

    public List<Product> findByCategory(Category category) {
        return namedParameterJdbcTemplate.query(
                ProductSql.getFindByCategoryQuery(),
                Collections.singletonMap("category", category.name()),
                productRowMapper);
    }
}
