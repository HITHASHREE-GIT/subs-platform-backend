package com.subsplatform.subs_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plan_usage_limits")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanUsageLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private Feature feature;

    @Column(name = "limit_quantity", nullable = false)
    private Integer limitQuantity;

    @Column(name = "overage_price", precision = 10, scale = 2)
    private BigDecimal overagePrice;
}