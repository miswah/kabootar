package io.kabootar.simulator.dto.request;

public record SimulatorRequestDTO(Integer fixedDelaysMs, Integer jitterMs, Double errorPercentage, Integer errorStatus, boolean outage, Integer maximumConcurrency, String correlationId) {
}