package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.entity.*;
import com.subsplatform.subs_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/lifecycle")
public class LifecycleController {

    @Autowired private SubscriptionRepository subscriptionRepository;
    @Autowired private CancellationRepository cancellationRepository;
    @Autowired private RenewalRepository renewalRepository;
    @Autowired private AuditLogRepository auditLogRepository;

    // 1. Cancel Subscription
    @PostMapping("/cancel/{subscriptionId}")
    public String cancelSubscription(@PathVariable Long subscriptionId, @RequestParam String reason) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        sub.setStatus(Subscription.Status.CANCELLED);
        subscriptionRepository.save(sub);

        Cancellation cancellation = Cancellation.builder()
                .subscription(sub)
                .reason(reason)
                .effectiveDate(LocalDate.now())
                .status(Cancellation.Status.PROCESSED)
                .build();
        cancellationRepository.save(cancellation);

        // Audit Log
        AuditLog log = AuditLog.builder()
                .entityType("SUBSCRIPTION")
                .entityId(subscriptionId)
                .action("CANCELLED")
                .performedBy(1L) // Hardcoded Admin ID for now
                .remarks("Reason: " + reason)
                .build();
        auditLogRepository.save(log);

        return "Subscription Cancelled successfully.";
    }

    // 2. Renew Subscription
    @PostMapping("/renew/{subscriptionId}")
    public String renewSubscription(@PathVariable Long subscriptionId) {
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        // Extend by 30 days (or plan validity)
        sub.setEndDate(sub.getEndDate().plusDays(sub.getPlan().getValidityDays()));
        sub.setStatus(Subscription.Status.ACTIVE);
        subscriptionRepository.save(sub);

        Renewal renewal = Renewal.builder()
                .subscription(sub)
                .renewalDate(LocalDate.now())
                .status(Renewal.Status.ACCEPTED)
                .customerDecision("RENEWED")
                .build();
        renewalRepository.save(renewal);

        // Audit Log
        AuditLog log = AuditLog.builder()
                .entityType("SUBSCRIPTION")
                .entityId(subscriptionId)
                .action("RENEWED")
                .performedBy(1L)
                .remarks("Subscription extended.")
                .build();
        auditLogRepository.save(log);

        return "Subscription Renewed successfully. New End Date: " + sub.getEndDate();
    }

    // 3. View Audit Logs
    @GetMapping("/audit-logs")
    public java.util.List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAll();
    }
}