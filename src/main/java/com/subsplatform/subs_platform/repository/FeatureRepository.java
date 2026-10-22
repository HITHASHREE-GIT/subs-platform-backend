package com.subsplatform.subs_platform.repository;

import com.subsplatform.subs_platform.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}