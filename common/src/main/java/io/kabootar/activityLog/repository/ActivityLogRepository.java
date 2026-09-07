package io.kabootar.activityLog.repository;

import io.kabootar.activityLog.models.ActivityLogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityLogRepository extends JpaRepository<ActivityLogModel, UUID> {
}
