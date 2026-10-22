package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalRepository extends JpaRepository<Renewal, Long> {
}