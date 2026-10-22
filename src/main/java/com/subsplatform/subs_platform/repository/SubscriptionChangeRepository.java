package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.SubscriptionChange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionChangeRepository extends JpaRepository<SubscriptionChange, Long> {
}