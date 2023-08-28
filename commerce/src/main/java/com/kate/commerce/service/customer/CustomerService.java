package com.kate.commerce.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kate.commerce.domain.customer.Customer;
import com.kate.commerce.exception.DuplicatedEmailException;
import com.kate.commerce.repository.customer.CustomerRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void validateDuplicatedCustomer(String email) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        if (optionalCustomer.isPresent()) {
            throw new DuplicatedEmailException("중복 이메일입니다.");
        }
    }
}
