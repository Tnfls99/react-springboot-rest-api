package com.prgrms.clone.cloneproject.repository;

import com.prgrms.clone.cloneproject.TestConfig;
import com.prgrms.clone.cloneproject.domain.Product;
import com.prgrms.clone.cloneproject.domain.util.Category;
import com.prgrms.clone.cloneproject.domain.util.Color;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
class JdbcProductRepositoryTest {

    @Autowired
    private JdbcProductRepository jdbcProductRepository;

    private static EmbeddedMysql embeddedMysql;
    private static MysqldConfig mysqldConfig;

    private static final String imageUrl = "www.google.com";

    @BeforeAll
    static void setjdbc() {
        mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2216)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("shop", classPathScript("product.sql"))
                .start();


    }

    @AfterEach
    void clean() {
        embeddedMysql.reloadSchema("shop", classPathScript("product.sql"));
    }

    @AfterAll
    static void exitTest() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void testInsertProduct() {
        // given
        Product product = new Product("product", Category.SHOES, 40000, Color.BLACK, 100, true, imageUrl);

        // when
        jdbcProductRepository.insert(product);

        // then
        Product findProduct = jdbcProductRepository.findById(product.getId()).get();
        assertThat(product).isEqualTo(findProduct);
    }

    @Test
    @DisplayName("모든 상품을 조회할 수 있다.")
    void testFindAllProducts() {
        // given
        Product product1 = new Product("product1", Category.BOTTOM, 10000, Color.BLACK, 100, true, imageUrl);
        Product product2 = new Product("product2", Category.ETC, 5000, Color.IVORY, 100, true, imageUrl);
        jdbcProductRepository.insert(product1);
        jdbcProductRepository.insert(product2);

        // when
        List<Product> productList = jdbcProductRepository.findAll();

        // then
        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList).isEqualTo(
                List.of(product1, product2));
    }

    @Test
    @DisplayName("id 값을 통해 특정 상품을 조회할 수 있다.")
    void testFindById() {
        // given
        Product product = new Product("product1", Category.ETC, 5000, Color.IVORY, 100, true, imageUrl);
        jdbcProductRepository.insert(product);

        // when
        Product findProduct = jdbcProductRepository.findById(product.getId()).get();

        // then
        assertThat(product).isEqualTo(findProduct);
    }

    @Test
    @DisplayName("id를 통해 특정 상품을 조회하여 이름을 수정할 수 있다.")
    void testUpdateProductName() {
        // given
        Product product = new Product("pruduct1", Category.BOTTOM, 40000, Color.BLACK, 100, true, imageUrl);
        jdbcProductRepository.insert(product);

        String newName = "new-product";
        product.changeName(newName);

        // when
        jdbcProductRepository.updateName(product);

        // then
        Product findProduct = jdbcProductRepository.findById(product.getId()).get();
        assertThat(findProduct.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("id를 통해 특정 상품을 삭제할 수 있다.")
    void testDeleteProduct() {
        // given
        Product product = new Product("product1", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        jdbcProductRepository.insert(product);

        // when
        jdbcProductRepository.deleteById(product.getId());

        // then
        assertThat(Optional.empty()).isEqualTo(
                jdbcProductRepository.findById(product.getId())
        );
    }

    @Test
    @DisplayName("가격이 낮은 순으로 조회할 수 있다.")
    void testOrderByLowPrice() {
        // given
        Product firstProduct = new Product("product1", Category.OUTER, 5000, Color.BLACK, 100, true, imageUrl);
        Product secondProduct = new Product("product2", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        jdbcProductRepository.insert(firstProduct);
        jdbcProductRepository.insert(secondProduct);

        // when
        List<Product> productOrderByLowPriceList = jdbcProductRepository.findAllOrderByLowPrice();

        // then
        assertThat(List.of(firstProduct, secondProduct))
                .containsSequence(productOrderByLowPriceList);
    }

    @Test
    @DisplayName("가격이 높은 순으로 조회할 수 있다.")
    void testOrderByHighPrice() {
        // given
        Product firstProduct = new Product("product1", Category.OUTER, 5000, Color.BLACK, 100, true, imageUrl);
        Product secondProduct = new Product("product2", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        jdbcProductRepository.insert(firstProduct);
        jdbcProductRepository.insert(secondProduct);

        // when
        List<Product> productOrderByHighPriceList = jdbcProductRepository.findAllOrderByHighPrice();

        // then
        assertThat(List.of(secondProduct, firstProduct))
                .containsSequence(productOrderByHighPriceList);
    }

    @Test
    @DisplayName("이름을 오름차순으로 조회할 수 있다.")
    void testOrderByName() {
        // given
        Product firstProduct = new Product("A", Category.OUTER, 5000, Color.BLACK, 100, true, imageUrl);
        Product secondProduct = new Product("B", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        jdbcProductRepository.insert(firstProduct);
        jdbcProductRepository.insert(secondProduct);

        // when
        List<Product> productOrderByNameList = jdbcProductRepository.findAllOrderByName();

        // then
        assertThat(List.of(firstProduct, secondProduct))
                .containsSequence(productOrderByNameList);
    }

    @Test
    @DisplayName("메이드 상품만 불러올 수 있다.")
    void testFindOnlyMadeProducts() {
        // given
        Product madeProduct = new Product("made", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        Product notMadeProduct = new Product("not-made", Category.OUTER, 10000, Color.IVORY, 100, false, imageUrl);
        jdbcProductRepository.insert(madeProduct);
        jdbcProductRepository.insert(notMadeProduct);

        // when
        List<Product> productOnlyMadeList = jdbcProductRepository.findAllMadeProduct();

        // then
        assertThat(madeProduct).isIn(productOnlyMadeList);
        assertThat(notMadeProduct).isNotIn(productOnlyMadeList);

    }

    @Test
    @DisplayName("카테고리별로 불러올 수 있다.")
    void testFindByCategory() {
        // given
        Product outerProduct = new Product("made", Category.OUTER, 10000, Color.BLACK, 100, true, imageUrl);
        Product dressProduct = new Product("not-made", Category.DRESS, 10000, Color.IVORY, 100, true, imageUrl);
        jdbcProductRepository.insert(outerProduct);
        jdbcProductRepository.insert(dressProduct);

        // when
        List<Product> productByCategoryList = jdbcProductRepository.findByCategory(outerProduct.getCategory());

        // then
        assertThat(outerProduct).isIn(productByCategoryList);
        assertThat(dressProduct).isNotIn(productByCategoryList);
    }
}