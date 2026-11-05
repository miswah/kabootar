package io.kabootar.configuration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.UUID;


@Entity
@Table(name="rate_limit_policy")
public class RateLimitPolicy extends ExtendedAuditable{
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="requests_per_window")
    @Min(value = 1, message = "minimum 1 request per window is needed.")
    private Long requestPerWindow;

    @Column(name="burst_capacity")
    @Min(value=0)
    private Long burstCapacity;

    @Column(name="enabled")
    private boolean isEnabled;
}