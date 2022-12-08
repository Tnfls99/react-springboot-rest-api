package com.prgrms.clone.cloneproject.customer.repository;

import com.prgrms.clone.cloneproject.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();

    Optional<Customer> findById(Integer customerId);

    void insert(Customer customer);

    void updateName(Customer customer);

    void updateEmail(Customer customer);

    void updateAddress(Customer customer);

    void deleteById(Integer customerId);

}
