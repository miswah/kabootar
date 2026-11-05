package io.kabootar.configuration.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="configuration_audit_event")
public class ConfigurationAuditEvent {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="event")
    private String event; //Define enum for this TBD

    @Column(name="metadata")
    private String metadata;


    @Column(name="created_by") // These should match with the other entities with ExtendedAuditable TBD
    private String createdBy;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}