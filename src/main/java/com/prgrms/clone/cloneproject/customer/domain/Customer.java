package com.prgrms.clone.cloneproject.customer.domain;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class Customer {

    private static final String EMAIL_PATTERN = "[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private Integer id;
    private String name;
    private String email;
    private String address;
    private Cart cart;

    public Customer(Integer id, String name, String email) {
        validateEmail(email);
        this.id = id;
        this.name = name;
        this.email = email;
        this.cart = new Cart(id);
    }

    public Customer(String name, String email) {
        this(null, name, email, null);
    }

    public Customer(Integer id, String name, String email, String address) {
        validateEmail(email);
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.cart = new Cart(id);
    }

    public Customer(String name, String email, String address){
        this(null, name, email, address);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Cart getCart() {
        return cart;
    }

    public void changeName(String newName){
        validateName(newName);
        this.name = newName;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeEmail(String newEmail){
        validateEmail(newEmail);
        this.email = newEmail;
    }

    public void setId(Integer generatedKey) {
        if (isNotNull()) {
            throw new IllegalStateException("id 값은 변경할 수 없습니다.");
        }
        id = generatedKey;
    }

    private boolean isNotNull() {
        return id != null;
    }

    private void validateEmail(String input){
        boolean result = emailPattern.matcher(input)
                .matches();
        if(!result){
            throw new IllegalArgumentException(
                    MessageFormat.format("이메일 형식이 올바르지 않습니다. 값을 다시 확인해주세요. [들어온값: {0}]", input));
        }
    }

    private void validateName(String input){
        if(input.isBlank()){
            throw new IllegalArgumentException("이름은 빈값일 수 없습니다.");
        }
    }


    public String descriptionParameter() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
