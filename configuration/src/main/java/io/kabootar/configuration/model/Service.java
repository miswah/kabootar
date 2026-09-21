package io.kabootar.configuration.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name="services", uniqueConstraints = {
        @UniqueConstraint(name="uk_region_key", columnNames="key" )
})
public class Service extends Auditable {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Size(max=255, message="Key size can't exceed 255 characters")
    @Column(name="key", nullable=false, length=255)
    private String key;

}
