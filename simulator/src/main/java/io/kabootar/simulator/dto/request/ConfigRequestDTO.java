package io.kabootar.simulator.dto.request;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Data
@Builder
public class ConfigRequestDTO{
    private Integer fixedDelaysMs;
    private Integer jitterMs;
    private Integer errorStatus;
    private Integer maximumConcurrency;

    @Max(value = 100)
    @Min(value = 0)
    private Double errorPercentage;
    private boolean outage;
}
