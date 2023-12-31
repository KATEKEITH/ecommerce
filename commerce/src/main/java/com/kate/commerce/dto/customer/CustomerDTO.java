package com.kate.commerce.dto.customer;

import com.kate.commerce.domain.customer.Customer;

import lombok.Data;

@Data(staticConstructor = "of")
public class CustomerDTO {
    private final Long customerId;
    private final String customerName;
    private final String email;
    private final String address;
    private final String phoneNumber;

    public static CustomerDTO of(Customer customer) {
        return CustomerDTO.of(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber());
    }
}
