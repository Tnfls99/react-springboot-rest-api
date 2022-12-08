package com.prgrms.clone.cloneproject.customer.domain;

import org.springframework.lang.Nullable;

public class CustomerPutDTO {
    @Nullable
    private String name;
    @Nullable
    private String address;
    @Nullable
    private String email;

    public CustomerPutDTO() {
    }

    public CustomerPutDTO(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
