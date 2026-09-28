package io.kabootar.configuration.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="service_instance")
public class ServiceInstance extends Auditable{

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Size(max=255, message="Key size can't exceed 255 characters")
    @Column(name="key", nullable=false, length=255)
    private String key;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_service_instance_region"))
    private List<Service> services = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_service_instance_region"))
    private Region region;
}