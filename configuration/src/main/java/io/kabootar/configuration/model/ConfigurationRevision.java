package io.kabootar.configuration.model;


import io.kabootar.configuration.enums.RevisionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="configuration_revision")
public class ConfigurationRevision extends ExtendedAuditable {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(name="revision_number", nullable = false, unique=true)
    private Long revisionNumber;

    @Column(name="status")
    private RevisionStatus status;

    @Column(name="based_on_revision_id" , length = 36, nullable = false)
    private UUID basedOnRevision;

    @Column(name="schema_version", length = 36, nullable = false)
    private UUID schemaVersion;

    @Column(name="checksum", length = 64, nullable = false)
    private String checksum;

    @Column(name="lock_version")
    @Version
    private Long lockVersion;

    @Column(name="published_by")
    private String publishedBy;

    @Column(name="published_at")
    private LocalDateTime publishedAt;

}