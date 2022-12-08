package com.prgrms.clone.cloneproject.customer.service;

import com.prgrms.clone.cloneproject.customer.domain.Customer;
import com.prgrms.clone.cloneproject.customer.domain.CustomerPutDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerDTO;
import com.prgrms.clone.cloneproject.customer.exception.DuplicateUserException;
import com.prgrms.clone.cloneproject.customer.repository.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class CustomerProvider {
    private final JdbcCustomerRepository customerRepository;

    public CustomerProvider(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(CustomerDTO customerDTO) {
        isRegisteredUser(customerDTO.getEmail());

        String name = customerDTO.getName();
        String email = customerDTO.getEmail();
        String address = customerDTO.getAddress();
        Customer customer = new Customer(name, email, address);

        customerRepository.insert(customer);
    }

    public void withdraw(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

    public void update(Integer customerId, CustomerPutDTO customerPutDTO) {
        changeCustomerName(customerId, customerPutDTO.getName());
        changeCustomerAddress(customerId, customerPutDTO.getAddress());
        changeCustomerEmail(customerId, customerPutDTO.getEmail());
    }

    public Customer findCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(
                            MessageFormat.format("해당 Id에 해당하는 고객을 찾을 수 없습니다. [ id :{0} ]", customerId));
                });
    }

    private void changeCustomerName(Integer customerId, String newName) {
        if (isNotNull(newName)) {
            Customer findCustomer = findCustomerById(customerId);
            findCustomer.changeName(newName);
            customerRepository.updateName(findCustomer);
        }
    }

    private void changeCustomerEmail(Integer customerId, String newEmail) {
        if (isNotNull(newEmail)) {
            Customer findCustomer = findCustomerById(customerId);
            findCustomer.changeEmail(newEmail);
            customerRepository.updateEmail(findCustomer);
        }
    }

    private void changeCustomerAddress(Integer customerId, String newAddress) {
        if (isNotNull(newAddress)) {
            Customer findCustomer = findCustomerById(customerId);
            findCustomer.changeAddress(newAddress);
            customerRepository.updateAddress(findCustomer);
        }
    }

    private void isRegisteredUser(String email) {
        if (customerRepository.isRegisteredUser(email)) {
            throw new DuplicateUserException("이미 사용중인 이메일입니다.");
        }
    }

    private boolean isNotNull(String input) {
        return input != null;
    }
}
