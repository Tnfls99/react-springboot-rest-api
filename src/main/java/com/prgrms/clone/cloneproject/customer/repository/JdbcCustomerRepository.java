package com.prgrms.clone.cloneproject.customer.repository;

import com.prgrms.clone.cloneproject.customer.domain.Customer;
import com.prgrms.clone.cloneproject.customer.repository.util.CustomerSql;
import com.prgrms.clone.cloneproject.product.repository.JdbcProductRepository;
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
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Integer UPDATE_SUCCESS = 1;
    private static final Integer NO_USER = 0;
    private static final Logger logger = LoggerFactory.getLogger(JdbcProductRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GeneratedKeyHolder generatedKeyHolder;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");
        String email = resultSet.getString("email");

        try {
            return new Customer(id, name, email, address);
        } catch (IllegalArgumentException illegalArgumentException) {
            String errorDetail = MessageFormat.format("결과를 가져올수 없습니다. 값들을 [id: {0}, name: {1}, address: {2}, email: {3}",
                    id, name, address, email);
            throw new IllegalStateException(errorDetail, illegalArgumentException);
        }
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.generatedKeyHolder = new GeneratedKeyHolder();
    }

    private static Map<String, Object> createUpdateParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", customer.getId());
        paramMap.put("name", customer.getName());
        paramMap.put("address", customer.getAddress());
        paramMap.put("eamil", customer.getEmail());

        return Collections.unmodifiableMap(paramMap);
    }

    private static SqlParameterSource createSqlParaMap(Customer customer) {
        return new MapSqlParameterSource()
                .addValue("name", customer.getName())
                .addValue("email", customer.getEmail())
                .addValue("address", customer.getAddress());
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query(
                CustomerSql.getAllQuery(), customerRowMapper
        );
    }

    @Override
    public Optional<Customer> findById(Integer customerId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            CustomerSql.getFindByIdQuery(),
                            Collections.singletonMap("id", customerId),
                            customerRowMapper));
        } catch (IllegalStateException illegalStateException) {
            logger.error(illegalStateException.getMessage(), illegalStateException);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.info("해당 id를 가진 고객이 없음. [id: {}]", customerId);
        }
        return Optional.empty();
    }

    @Override
    public void insert(Customer customer) {
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getInsertQuery(), createSqlParaMap(customer), generatedKeyHolder, new String[]{"id"});
        if (update != UPDATE_SUCCESS) {
            throw new IllegalStateException("내부 문제 발생으로 상품을 등록할 수 없습니다.");
        }
        Integer key = Objects.requireNonNull(generatedKeyHolder.getKey()).intValue();
        customer.setId(key);
    }

    @Override
    public void updateName(Customer customer) {
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getChangeNameQuery(), createUpdateParamMap(customer)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalArgumentException(
                    MessageFormat.format("이름을 변경할 수 없습니다. 값을 다시 확인해주세요. [들어온 값 :{0}]",
                            customer.descriptionParameter()));
        }
    }

    public void updateEmail(Customer customer) {
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getChangeEmailQuery(), createUpdateParamMap(customer)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalArgumentException(
                    MessageFormat.format("이메일을 변경할 수 없습니다. 값을 다시 확인해주세요. [들어온 값 :{0}]",
                            customer.descriptionParameter()));
        }
    }

    @Override
    public void updateAddress(Customer customer) {
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getChangeAddresQuery(), createUpdateParamMap(customer)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalArgumentException(
                    MessageFormat.format("주소를 변경할 수 없습니다. 값을 다시 확인해주세요. [들어온 값 :{0}]",
                            customer.descriptionParameter()));
        }
    }

    @Override
    public void deleteById(Integer customerId) {
        int update = namedParameterJdbcTemplate.update(
                CustomerSql.getDeleteQuery(),
                Collections.singletonMap("id", customerId)
        );

        if (update != UPDATE_SUCCESS) {
            throw new IllegalArgumentException("입력받은 id에 해당하는 고객이 없기 때문에 삭제할 수 없습니다.");
        }
    }

    public Boolean isRegisteredUser(String email) {
        int count = namedParameterJdbcTemplate.queryForObject(
                CustomerSql.getCountUserQuery(), Collections.singletonMap("email", email), Integer.class
        );

        return count != NO_USER;
    }
}
