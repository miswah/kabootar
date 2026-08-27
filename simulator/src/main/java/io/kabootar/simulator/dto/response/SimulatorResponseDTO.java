package io.kabootar.simulator.dto.response;

public record SimulatorResponseDTO (String service, String region, String instance, String correlationId, String timestamp) {
}
