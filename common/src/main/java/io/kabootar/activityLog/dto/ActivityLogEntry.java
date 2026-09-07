package io.kabootar.activityLog.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.kabootar.activityLog.enums.Outcome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogEntry {

    private String userId;
    private String userName;
    private Long userModelId;
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private String remoteAddr;
    private String httpMethod;
    private String path;
    private String moduleName;
    private String actionName;
    private String targetId;
    private String outcome;
    private Integer durationMs;
    private String params;
    private String extra;
    private String createdBy;
    private String traceId;

    // --- convenience methods ---

    public void markSuccess() {
        this.outcome = String.valueOf(Outcome.SUCCESS);
        this.timestamp = OffsetDateTime.now();
    }

    public void markFailure(Throwable ex) {
        this.outcome = String.valueOf(Outcome.EXCEPTION) + ": " + ex.getClass().getSimpleName();
        this.timestamp = OffsetDateTime.now();
    }

    public void setDurationFrom(long startMillis) {
        this.durationMs = (int)(System.currentTimeMillis() - startMillis);
    }

    public boolean hasActor() {
        return userId != null && !userId.isBlank();
    }

    public boolean isFailure() {
        return outcome != null && outcome.startsWith("EXCEPTION");
    }

    public void captureParams(Object[] args, ObjectMapper mapper) {
        try {
            // filter out unwanted types
            Object[] safeArgs = java.util.Arrays.stream(args)
//                    .filter(arg -> !(arg instanceof javax.servlet.http.HttpServletRequest
//                            || arg instanceof javax.servlet.http.HttpServletResponse
//                            || arg instanceof org.springframework.web.multipart.MultipartFile))
//
                    .toArray();
            this.params = mapper.writeValueAsString(safeArgs);
        } catch (Exception e) {
            this.params = "[unserializable-args]";
        }
    }

    @Override
    public String toString() {
        return "ActivityLogEntry{" +
                "actorId='" + userId + '\'' +
                ", action='" + actionName + '\'' +
                ", module='" + moduleName + '\'' +
                ", path='" + path + '\'' +
                ", outcome='" + outcome + '\'' +
                ", durationMs=" + durationMs +
                '}';
    }
}
