package io.kabootar.simulator.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ConfigRequestDTO(Integer fixedDelaysMs, Integer jitterMs, Integer errorStatus, Integer maximumConcurrency, @Max(value= 100) @Min(value = 0) Double errorPercentage, boolean outage){}
