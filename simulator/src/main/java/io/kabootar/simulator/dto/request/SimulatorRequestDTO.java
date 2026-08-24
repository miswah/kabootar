package io.kabootar.simulator.dto.request;

public record SimulatorRequestDTO(Integer fixedDelaysMs, Integer jitterMs, Integer errorPercentage, Integer errorStatus, boolean outage, Integer maximumConcurrency, String correlationId) {
}