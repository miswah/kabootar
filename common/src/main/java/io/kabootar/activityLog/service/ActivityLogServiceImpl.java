package io.kabootar.activityLog.service;

import io.kabootar.activityLog.dto.ActivityLogResponseDTO;
import io.kabootar.activityLog.interfaces.ActivityLogService;
import io.kabootar.activityLog.models.ActivityLogModel;
import io.kabootar.activityLog.repository.ActivityLogRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository repo;
    public ActivityLogServiceImpl(ActivityLogRepository repo) { this.repo = repo; }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(ActivityLogModel log) {
        repo.save(log);
    }

    @Override
    public List<ActivityLogResponseDTO> getLogs() {
        return this.mapToDtoList(this.repo.findAll());
    }

    private ActivityLogResponseDTO mapToDto(ActivityLogModel m){
        return new ActivityLogResponseDTO(m.getUserId(), m.getUserName(), m.getTimestamp(), m.getRemoteAddr(),
                m.getHttpMethod(), m.getPath(), m.getModuleName(), m.getActionName(), m.getOutcome(), m.getDurationMs(),
                m.getParams(), m.getTraceId());
    }

    private List<ActivityLogResponseDTO> mapToDtoList(List<ActivityLogModel> model){
        return model.stream().map(this::mapToDto).toList();
    }
}
