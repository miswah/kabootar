package io.kabootar.configuration.model;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="security_policy")
public class SecurityPolicy extends ExtendedAuditable{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="revision_id", referencedColumnName="id")
    private ConfigurationRevision revision;

    @Column(name="authentication_required")
    private boolean isAuthenticationRequired;

    @Column(name="ssl_enabled")
    private boolean isSslEnabled;

    @Column(name="allowed_origins")
    private String AllowedOrigins;

    @Column(name="configuration")
    private String configuration; //Same TBD

    @Column(name="enabled")
    private boolean isEnabled;
}