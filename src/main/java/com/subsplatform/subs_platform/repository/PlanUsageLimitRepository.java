package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.PlanUsageLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlanUsageLimitRepository extends JpaRepository<PlanUsageLimit, Long> {
    Optional<PlanUsageLimit> findByPlanIdAndFeatureId(Long planId, Long featureId);
}