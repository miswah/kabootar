package io.kabootar.simulator.service.impl;

import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.exceptions.IntentionalFailureException;
import io.kabootar.simulator.service.SimulatorService;
import io.kabootar.simulator.utilities.ErrorPercentageCalculator;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SimulatorServiceImpl implements SimulatorService {




    @Override
    public SimulatorResponseDTO getInstance(SimulatorRequestDTO dto) throws InterruptedException {
        String correlationId = dto.correlationId() == null  ? UUID.randomUUID().toString() : dto.correlationId();

        int fixedDelayMs = dto.fixedDelaysMs();
        int jitterMs = dto.jitterMs();

        if(fixedDelayMs != 0 || jitterMs != 0){
            int randomDelayMs = ThreadLocalRandom.current().nextInt(0, jitterMs + 1);
            Thread.sleep(fixedDelayMs + randomDelayMs);
        }

        if(ErrorPercentageCalculator.shouldFail(dto.errorPercentage())){
            throw new IntentionalFailureException("Injected failure");
        }


    }
}
