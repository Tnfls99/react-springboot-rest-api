package com.prgrms.clone.cloneproject.customer.service;

import com.prgrms.clone.cloneproject.customer.domain.Cart;
import com.prgrms.clone.cloneproject.customer.domain.CartItem;
import com.prgrms.clone.cloneproject.customer.domain.Customer;
import com.prgrms.clone.cloneproject.customer.domain.dto.CartItemDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerDTO;
import com.prgrms.clone.cloneproject.customer.domain.dto.CustomerPutDTO;
import com.prgrms.clone.cloneproject.customer.exception.DuplicateUserException;
import com.prgrms.clone.cloneproject.customer.exception.NoAuthenticatedException;
import com.prgrms.clone.cloneproject.customer.repository.JdbcCartRepository;
import com.prgrms.clone.cloneproject.customer.repository.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class CustomerProvider {

    private static final Integer NO_HEADER = -1;

    private final JdbcCustomerRepository customerRepository;
    private final JdbcCartRepository cartRepository;

    public CustomerProvider(JdbcCustomerRepository customerRepository, JdbcCartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    public static void isValidCustomer(Integer customerId) {
        if (Objects.equals(customerId, NO_HEADER)) {
            throw new NoAuthenticatedException("고객정보가 없어 요청하신 것을 수행할 수 없습니다.");
        }
    }

    public void save(CustomerDTO customerDTO) {
        isRegisteredUser(customerDTO.getEmail());

        String name = customerDTO.getName();
        String email = customerDTO.getEmail();
        String address = customerDTO.getAddress();
        Customer customer = new Customer(name, email, address);

        customerRepository.insert(customer);

        Cart newCart = new Cart(customer.getId());
        cartRepository.insert(newCart);
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

    public void addProductToCart(CartItemDTO cartItemDTO, Integer customerId) {
        Cart cart = findCart(customerId);

        if (isContainProduct(cart, cartItemDTO)) {
            CartItem findItem = cart.getCartItems()
                    .stream()
                    .filter(cartItem -> Objects.equals(cartItem.getProductId(), cartItemDTO.getProductId()))
                    .findAny()
                    .orElseThrow(() -> {
                        throw new IllegalArgumentException("장바구니에서 해당 상품 정보를 불러올 수 없습니다.");
                    });
            findItem.changeQuantity(
                    cartItemDTO.getQuantity());

            cartRepository.updateCartItem(findItem);
            return;
        }

        CartItem newCartItem = new CartItem(
                cart.getId(),
                cartItemDTO.getProductId(),
                cartItemDTO.getQuantity(),
                cartItemDTO.getPrice());

        cartRepository.insertCartItem(newCartItem);
    }

    public Cart findCart(Integer customerId) {
        return cartRepository.findCartByCustomerId(customerId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(
                            MessageFormat.format("고객을 찾을 수 없어 장바구니를 불러올 수 없습니다. [요청된 고객 id: [{0}]]", customerId));
                });
    }

    private boolean isContainProduct(Cart cart, CartItemDTO cartItemDTO) {
        return cart.getCartItems()
                .stream()
                .anyMatch(cartItem ->
                        Objects.equals(
                                cartItem.getCartId(), cart.getId())
                                && Objects.equals(
                                cartItem.getProductId(), cartItemDTO.getProductId()));

    }
}
