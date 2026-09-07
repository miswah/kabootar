package io.kabootar.activityLog.models;


import io.kabootar.utilities.models.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Table(name = "activity_log")
@Entity
public class ActivityLogModel extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "timestamp")
    private OffsetDateTime timestamp = OffsetDateTime.now();

    @Column(name ="client_ip")
    private String remoteAddr;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "path")
    private String path;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "target_id")
    private String targetId; //optional id of acted-upon resource

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "duration")
    private Integer durationMs;

    @Column(name = "parameters", columnDefinition = "text")
    private String params;

    @Column(name = "extra", columnDefinition = "text")
    private String extra;

    @Column(name = "trace_id")
    private String traceId;
}
