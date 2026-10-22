package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}