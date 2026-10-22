package com.subsplatform.subs_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "renewals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Renewal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(name = "renewal_date", nullable = false)
    private LocalDate renewalDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "customer_decision")
    private String customerDecision;

    public enum Status { PENDING, ACCEPTED, DECLINED }
}