package io.kabootar.activityLog.interfaces;

import io.kabootar.activityLog.dto.ActivityLogResponseDTO;
import io.kabootar.activityLog.models.ActivityLogModel;

import java.util.List;

public interface ActivityLogService {
    public void save(ActivityLogModel log);
    public List<ActivityLogResponseDTO> getLogs();
}
