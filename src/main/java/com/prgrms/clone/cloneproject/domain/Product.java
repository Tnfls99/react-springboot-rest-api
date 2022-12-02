package com.prgrms.clone.cloneproject.domain;

import com.prgrms.clone.cloneproject.domain.util.Category;
import com.prgrms.clone.cloneproject.domain.util.Color;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Product {

    private static final Integer MIN_PRICE = 0;
    private static final Integer SOLD_OUT = 0;

    private final Category category;
    private final Integer price;
    private final Color color;
    private final LocalDateTime registeredAt;
    private final Boolean isMade;

    private Integer id;
    private String name;
    private Integer stock;
    private String imageUrl;

    public Product(Integer id, String name, Category category, Integer price, Color color, Integer stock, Boolean isMade, LocalDateTime registeredAt, String imageUrl) {
        validateName(name);
        validatePrice(price);
        validateStock(stock);
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.color = color;
        this.stock = stock;
        this.isMade = isMade;
        this.imageUrl = imageUrl;
        this.registeredAt = registeredAt;
    }

    public Product(String name, Category category, Integer price, Color color, Integer stock, Boolean isMade, String imageUrl) {
        this(null, name, category, price, color, stock, isMade, ZonedDateTime.now(
                        ZoneId.of("Asia/Seoul"))
                .toLocalDateTime()
                .truncatedTo(
                        ChronoUnit.SECONDS), imageUrl);
    }

    public Boolean isSoldOut() {
        return Objects.equals(stock, SOLD_OUT);
    }

    public Boolean isAvailable() {
        return !Objects.equals(stock, SOLD_OUT);
    }

    public void sold() {
        this.stock -= 1;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Color getColor() {
        return color;
    }

    public Integer getStock() {
        return stock;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public Boolean getIsMade() {
        return isMade;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Integer generatedKey) {
        if (isIdNotNull()) {
            throw new IllegalStateException("id 값은 변경할 수 없습니다.");
        }
        id = generatedKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && category == product.category && Objects.equals(price, product.price) && color == product.color && Objects.equals(stock, product.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, color, stock);
    }

    private void validatePrice(Integer price) {
        if (price <= MIN_PRICE) {
            throw new IllegalArgumentException("값은 0보다 커야합니다.");
        }
    }

    private void validateStock(Integer stock) {
        if (stock < SOLD_OUT) {
            throw new IllegalArgumentException("수량은 음수이면 안됩니다.");
        }
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("이름은 빈값일 수 없습니다.");
        }
    }

    private boolean isIdNotNull() {
        return id != null;
    }
}
