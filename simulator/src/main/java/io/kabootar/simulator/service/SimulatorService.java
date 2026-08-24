package io.kabootar.simulator.service;

import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface SimulatorService {
    public SimulatorResponseDTO getInstance(SimulatorRequestDTO dto);
}
