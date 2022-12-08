package com.prgrms.clone.cloneproject.product.domain;

import javax.validation.constraints.NotNull;

public class ProductDTO {
    @NotNull(message = "빈 값일 수 없습니다.")
    private String name;
    @NotNull(message = "빈 값일 수 없습니다.")
    private Integer price;
    @NotNull(message = "빈 값일 수 없습니다.")
    private String color;
    @NotNull(message = "빈 값일 수 없습니다.")
    private String imageUrl;
    @NotNull(message = "빈 값일 수 없습니다.")
    private Integer stock;
    @NotNull(message = "빈 값일 수 없습니다.")
    private String category;
    @NotNull(message = "빈 값일 수 없습니다.")
    private Boolean isMade;


    public ProductDTO(String name, Integer price, String color, String imageUrl, Integer stock, String category, Boolean isMade) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.category = category;
        this.isMade = isMade;
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getIsMade() {
        return isMade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsMade(Boolean isMade) {
        this.isMade = isMade;
    }
}
