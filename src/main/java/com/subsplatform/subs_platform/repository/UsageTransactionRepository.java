package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.UsageTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsageTransactionRepository extends JpaRepository<UsageTransaction, Long> {
    List<UsageTransaction> findBySubscriptionIdAndFeatureId(Long subscriptionId, Long featureId);
}