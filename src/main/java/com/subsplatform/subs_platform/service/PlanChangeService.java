package com.subsplatform.subs_platform.service;

import com.subsplatform.subs_platform.entity.*;
import com.subsplatform.subs_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PlanChangeService {

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private PlanRepository planRepository;
    @Autowired private SubscriptionChangeRepository subscriptionChangeRepository;

    public SubscriptionChange changePlan(Long subscriptionId, Long newPlanId) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        Plan newPlan = planRepository.findById(newPlanId)
                .orElseThrow(() -> new RuntimeException("New Plan not found"));

        Plan oldPlan = sub.getPlan();

        // Update the subscription to the new plan
        sub.setPlan(newPlan);
        subscriptionRepository.save(sub);

        // Determine if it's an Upgrade or Downgrade based on price
        SubscriptionChange.ChangeType type = newPlan.getPrice().compareTo(oldPlan.getPrice()) > 0
                ? SubscriptionChange.ChangeType.UPGRADE
                : SubscriptionChange.ChangeType.DOWNGRADE;

        // Save the history
        SubscriptionChange change = SubscriptionChange.builder()
                .subscription(sub)
                .oldPlan(oldPlan)
                .newPlan(newPlan)
                .changeType(type)
                .effectiveDate(LocalDate.now())
                .status(SubscriptionChange.Status.APPROVED)
                .build();

        return subscriptionChangeRepository.save(change);
    }
}