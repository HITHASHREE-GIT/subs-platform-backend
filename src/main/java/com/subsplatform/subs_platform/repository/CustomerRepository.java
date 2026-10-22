package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}