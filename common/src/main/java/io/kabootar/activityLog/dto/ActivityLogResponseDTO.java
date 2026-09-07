package io.kabootar.activityLog.dto;

import java.time.OffsetDateTime;

public record ActivityLogResponseDTO(String userId, String userName, OffsetDateTime timestamp, String clientIp, String httpMethod, String path, String moduleName, String actionName, String outcome, Integer duration, String params, String traceId) {
}

