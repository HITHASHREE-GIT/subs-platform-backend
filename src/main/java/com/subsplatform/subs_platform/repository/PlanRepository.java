package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}