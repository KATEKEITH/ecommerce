package com.kate.commerce.domain.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kate.commerce.exception.NotFoundCustomerException;
import com.kate.commerce.repository.customer.CustomerRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(">>> 회원 정보 찾기, {}", email);
        Customer customer = this.customerRepository.findByEmail(email);
        if (customer == null) {
            throw new NotFoundCustomerException("회원 정보를 찾을 수 없습니다." + email);
        }

        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setCustomer(customer);

        return customerDetail;
    }
}
