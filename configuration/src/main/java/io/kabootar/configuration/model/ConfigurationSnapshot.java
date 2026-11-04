package io.kabootar.configuration.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name="configuration_snapshot")
public class ConfigurationSnapshot {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="schema_version")
    private UUID schemaVersion;

    @Column(name="checksum")
    private String checksum;

    @Column(name="regions")
    private String regions; //should this be not JPA entity instead?? TBD

    @Column(name="services")
    private String services;

    @Column(name="service_instances")
    private String serviceInstance;

    @Column(name="routes")
    private String routes;

    @Column(name="created_by") // These should match with the other entities with ExtendedAuditable TBD
    private String createdBy;

    @Column(name="created_at")
    private LocalDateTime createdAt;

}