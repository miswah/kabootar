package io.kabootar.simulator.dto.response;

import java.util.UUID;

public record SimulatorResponseDTO (String service, String region, String instance, UUID correlationId, String timestamp) {
}
