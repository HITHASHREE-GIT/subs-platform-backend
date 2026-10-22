package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.entity.*;
import com.subsplatform.subs_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private PlanRepository planRepository;
    @Autowired private UsageTransactionRepository usageTransactionRepository;
    @Autowired private AuditLogRepository auditLogRepository; // Added this line

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();

        List<Subscription> activeSubs = subscriptionRepository.findAll().stream()
                .filter(s -> s.getStatus() == Subscription.Status.ACTIVE)
                .collect(Collectors.toList());

        // 1. Total Active Subscriptions
        dashboard.put("totalActiveSubscriptions", activeSubs.size());

        // 2. Total Revenue (Estimated from active plans)
        BigDecimal totalRevenue = activeSubs.stream()
                .map(s -> s.getPlan().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboard.put("estimatedMonthlyRevenue", totalRevenue);

        // 3. Most Popular Plan
        String popularPlan = activeSubs.stream()
                .collect(Collectors.groupingBy(s -> s.getPlan().getName(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
        dashboard.put("mostPopularPlan", popularPlan);

        // 4. Total Usage Consumed
        List<UsageTransaction> allUsage = usageTransactionRepository.findAll();
        int totalUsage = allUsage.stream().mapToInt(UsageTransaction::getQuantity).sum();
        dashboard.put("totalUsageConsumed", totalUsage);

        // 5. Overage Events
        long overageCount = allUsage.stream()
                .filter(u -> u.getValidationStatus() == UsageTransaction.ValidationStatus.OVERAGE)
                .count();
        dashboard.put("totalOverageEvents", overageCount);

        // 6. Total Cancellations (NEW)
        long totalCancellations = auditLogRepository.findAll().stream()
                .filter(log -> log.getAction().equals("CANCELLED"))
                .count();
        dashboard.put("totalCancellations", totalCancellations);

        // 7. Total Renewals (NEW)
        long totalRenewals = auditLogRepository.findAll().stream()
                .filter(log -> log.getAction().equals("RENEWED"))
                .count();
        dashboard.put("totalRenewals", totalRenewals);

        return dashboard;
    }
}