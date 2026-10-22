package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}