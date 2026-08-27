package io.kabootar.simulator.dto.request;

public record ConfigRequestDTO(Integer fixedDelaysMs, Integer jitterMs, Double errorPercentage, Integer errorStatus, boolean outage, Integer maximumConcurrency) { }
