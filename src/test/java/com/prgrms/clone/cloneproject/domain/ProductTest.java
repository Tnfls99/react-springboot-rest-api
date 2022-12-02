package com.prgrms.clone.cloneproject.domain;

import com.prgrms.clone.cloneproject.product.util.Category;
import com.prgrms.clone.cloneproject.product.util.Color;
import com.prgrms.clone.cloneproject.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private static final String imageUrl = "www.google.com";

    @DisplayName("가격은 0보다 작으면 안된다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -100, -100000})
    void testPriceMoreThanZero(Integer price) {
        assertThatThrownBy(() -> {
            new Product("product1", Category.OUTER, price, Color.IVORY, 100, true, imageUrl);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("재고는 음수일 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -100000})
    void testStockMoreThanMinus(Integer stock) {
        assertThatThrownBy(() -> {
            new Product("product1", Category.OUTER, 1000, Color.IVORY, stock, true, imageUrl);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름을 빈값으로 변경할 수 없다.")
    @Test
    void testNotChangeNameWithEmpty() {
        Product product = new Product("product1", Category.DRESS, 10000, Color.BLACK, 100, true, imageUrl);

        assertThatThrownBy(() -> {
            product.changeName("");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름을 변경할 수 있다.")
    @Test
    void testChangeName() {
        Product product = new Product("product1", Category.SHOES, 10000, Color.BLACK, 100, true, imageUrl);
        String newName = "new-name";

        product.changeName(newName);

        assertThat(product.getName()).isEqualTo(newName);
    }
}