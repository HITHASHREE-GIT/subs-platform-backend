package com.subsplatform.subs_platform.controller;

import com.subsplatform.subs_platform.entity.Plan;
import com.subsplatform.subs_platform.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    private PlanRepository planRepository;

    // Create a new plan
    @PostMapping
    public Plan createPlan(@RequestBody Plan plan) {
        return planRepository.save(plan);
    }

    // Get all plans
    @GetMapping
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
}