package com.prgrms.clone.cloneproject.customer.controller;

import com.prgrms.clone.cloneproject.customer.domain.Customer;
import com.prgrms.clone.cloneproject.customer.domain.CustomerPutDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerDTO;
import com.prgrms.clone.cloneproject.customer.service.CustomerProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("shop/customers")
public class CustomerController {

    private final CustomerProvider customerProvider;

    public CustomerController(CustomerProvider customerProvider) {
        this.customerProvider = customerProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody CustomerDTO customerDTO) {
        customerProvider.save(customerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("회원가입에 성공하였습니다.");
    }

    @GetMapping("/{customerId}")
    public ResponseEntity customerDetail(@PathVariable Integer customerId) {
        Customer customer = customerProvider.findCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity delete(@PathVariable Integer userId) {
        customerProvider.withdraw(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 정보가 삭제되었습니다.");
    }

    @PutMapping("/{customerId}")
    public ResponseEntity changeInfo(@PathVariable Integer customerId, @RequestBody CustomerPutDTO customerPutDTO, UriComponentsBuilder uriComponentsBuilder) {
        customerProvider.update(customerId, customerPutDTO);
        URI location = uriComponentsBuilder.path("/shop/customers/{customerId}")
                .buildAndExpand(customerId).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("정보가 변경되었습니다.");
    }

}
