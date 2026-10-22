package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.dto.CustomerRequest;
import com.subsplatform.subs_platform.entity.Customer;
import com.subsplatform.subs_platform.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Customer register(@RequestBody CustomerRequest request) {
        return customerService.registerCustomer(request);
    }
}