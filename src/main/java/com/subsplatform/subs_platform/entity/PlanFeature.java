package com.subsplatform.subs_platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_features")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;
}