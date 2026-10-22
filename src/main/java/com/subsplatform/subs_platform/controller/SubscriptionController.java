package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.dto.SubscriptionRequest;
import com.subsplatform.subs_platform.entity.Customer;
import com.subsplatform.subs_platform.entity.Plan;
import com.subsplatform.subs_platform.entity.Subscription;
import com.subsplatform.subs_platform.repository.CustomerRepository;
import com.subsplatform.subs_platform.repository.PlanRepository;
import com.subsplatform.subs_platform.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private PlanRepository planRepository;

    @PostMapping
    public Subscription subscribe(@RequestBody SubscriptionRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription sub = Subscription.builder()
                .customer(customer)
                .plan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(plan.getValidityDays()))
                .status(Subscription.Status.ACTIVE)
                .autoRenew(true)
                .build();

        return subscriptionRepository.save(sub);
    }
}