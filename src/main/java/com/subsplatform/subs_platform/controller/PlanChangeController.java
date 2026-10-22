package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.entity.SubscriptionChange;
import com.subsplatform.subs_platform.service.PlanChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class PlanChangeController {

    @Autowired
    private PlanChangeService planChangeService;

    @PostMapping("/{subscriptionId}/change-plan/{newPlanId}")
    public SubscriptionChange changePlan(
            @PathVariable Long subscriptionId,
            @PathVariable Long newPlanId) {
        return planChangeService.changePlan(subscriptionId, newPlanId);
    }
}