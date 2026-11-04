package io.kabootar.configuration.model;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name="traffic_policy")
public class TrafficPolicy extends ExtendedAuditable {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="strategy")
    private String strategy;

    @Column(name="configuration")
    private String configuration; //Should this be a json or base64 type instead?? TBD

    @Column(name="enabled")
    private boolean isEnabled;
}