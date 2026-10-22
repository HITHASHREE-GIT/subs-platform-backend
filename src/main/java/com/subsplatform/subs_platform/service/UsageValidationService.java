package com.subsplatform.subs_platform.service;

import com.subsplatform.subs_platform.dto.UsageRequest;
import com.subsplatform.subs_platform.entity.*;
import com.subsplatform.subs_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UsageValidationService {

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private FeatureRepository featureRepository;
    @Autowired private UsageTransactionRepository usageTransactionRepository;
    @Autowired private PlanUsageLimitRepository planUsageLimitRepository; // Added

    public String validateAndConsume(UsageRequest request) {
        // 1. Find active subscription
        Subscription sub = subscriptionRepository.findAll().stream()
                .filter(s -> s.getCustomer().getId().equals(request.getCustomerId()) && s.getStatus() == Subscription.Status.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active subscription found for customer."));

        // 2. Check feature
        Feature feature = featureRepository.findById(request.getFeatureId())
                .orElseThrow(() -> new RuntimeException("Feature not found."));

        // 3. NEW: Check limit
        UsageTransaction.ValidationStatus status = UsageTransaction.ValidationStatus.ACCEPTED;
        String remarks = "Usage successful";

        PlanUsageLimit limit = planUsageLimitRepository
                .findByPlanIdAndFeatureId(sub.getPlan().getId(), feature.getId())
                .orElse(null);

        if (limit != null) {
            // Calculate how much they have already used
            int alreadyUsed = usageTransactionRepository.findBySubscriptionIdAndFeatureId(sub.getId(), feature.getId())
                    .stream()
                    .mapToInt(UsageTransaction::getQuantity)
                    .sum();

            int remaining = limit.getLimitQuantity() - alreadyUsed;

            if (request.getQuantity() > remaining) {
                status = UsageTransaction.ValidationStatus.OVERAGE;
                remarks = "Overage! Remaining limit was " + remaining + ". Charged extra.";
            }
        }

        // 4. Save transaction
        UsageTransaction tx = UsageTransaction.builder()
                .subscription(sub)
                .feature(feature)
                .quantity(request.getQuantity())
                .timestamp(LocalDateTime.now())
                .validationStatus(status)
                .remarks(remarks)
                .build();

        usageTransactionRepository.save(tx);

        return "Status: " + status + " | " + remarks;
    }
}