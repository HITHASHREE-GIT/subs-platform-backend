package com.subsplatform.subs_platform.service;

import com.subsplatform.subs_platform.dto.CustomerRequest;
import com.subsplatform.subs_platform.entity.*;
import com.subsplatform.subs_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired private UserRepository userRepository;
    @Autowired private CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // In production, hash this!
                .fullName(request.getFullName())
                .role(User.Role.CUSTOMER)
                .status(User.Status.ACTIVE)
                .build();
        user = userRepository.save(user);

        Customer customer = Customer.builder()
                .user(user)
                .phone(request.getPhone())
                .address(request.getAddress())
                .kycStatus(Customer.KycStatus.PENDING)
                .eligibility(true)
                .build();

        return customerRepository.save(customer);
    }
}