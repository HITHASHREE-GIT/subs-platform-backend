package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellationRepository extends JpaRepository<Cancellation, Long> {
}