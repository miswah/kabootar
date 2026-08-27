package io.kabootar.simulator.service.interfaces;

import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public interface SimulatorService {
    public Future<SimulatorResponseDTO> submit(SimulatorRequestDTO dto) throws InterruptedException;
}
