package com.prgrms.clone.cloneproject.customer.controller;

import com.prgrms.clone.cloneproject.customer.domain.Cart;
import com.prgrms.clone.cloneproject.customer.domain.Customer;
import com.prgrms.clone.cloneproject.customer.domain.dto.CartDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CartItemDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerPutDTO;
import com.prgrms.clone.cloneproject.customer.service.CustomerProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static com.prgrms.clone.cloneproject.customer.service.CustomerProvider.isValidCustomer;

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
    public ResponseEntity delete(@PathVariable Integer customerId) {
        customerProvider.withdraw(customerId);

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

    @GetMapping("/{customerId}/cart")
    public ResponseEntity cart(@PathVariable Integer customerId){
        Cart cart = customerProvider.findCart(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PostMapping("/{customerId}/cart/new")
    public ResponseEntity addProduct(@PathVariable Integer customerId, @RequestBody CartItemDTO cartItemDTO, UriComponentsBuilder uriComponentsBuilder){
        customerProvider.addProductToCart(cartItemDTO, customerId);

        URI location = uriComponentsBuilder.path("/shop/customers/{customerId}/cart")
                .buildAndExpand(customerId).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("장바구니에 상품을 추가합니다.");
    }

    @PostMapping("/{customerId}/cart")
    public ResponseEntity createOrderByCart(@RequestBody CartDTO cartDTO, UriComponentsBuilder uriComponentsBuilder, HttpServletRequest request){
        Integer customerId = request.getIntHeader("customerId");

        isValidCustomer(customerId);

        URI location = uriComponentsBuilder.path("/shop/customers/{customerId}/cart/order")
                .buildAndExpand(customerId).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(headers).build();
    }

}
