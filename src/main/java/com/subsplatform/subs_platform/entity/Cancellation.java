package com.subsplatform.subs_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cancellations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reason;

    @Column(name = "requested_date")
    private LocalDateTime requestedDate;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @PrePersist
    public void prePersist() {
        if (this.requestedDate == null) this.requestedDate = LocalDateTime.now();
        if (this.status == null) this.status = Status.REQUESTED;
    }
    public enum Status { REQUESTED, PROCESSED }
}