package io.kabootar.configuration.model;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name="tenant")
public class Tenant extends ExtendedAuditable{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="display_name")
    private String displayName;

    @Column(name="tenant_desc")
    private String tenantDescription;

}