package io.kabootar.simulator.service.impl;

import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.exceptions.IntentionalFailureException;
import io.kabootar.simulator.exceptions.QueueFullException;
import io.kabootar.simulator.service.SimulatorService;
import io.kabootar.simulator.utilities.ErrorPercentageCalculator;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SimulatorServiceImpl implements SimulatorService {
    private final ThreadPoolExecutor executor;

    public SimulatorServiceImpl(){
        int maxConcurrency = 5; // should be overridden from properties??
        int queueSize = 1;

        this.executor = new ThreadPoolExecutor(
                maxConcurrency,          // core pool size
                maxConcurrency,          // max pool size
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Override
    public Future<SimulatorResponseDTO> submit(SimulatorRequestDTO dto) {
        try {
            return executor.submit(() -> {
                return this.getInstance(dto);
            });
        } catch (RejectedExecutionException e) {
            throw new QueueFullException(
                    "Simulator is busy. Queue is full."
            );
        }
    }


    private SimulatorResponseDTO getInstance(SimulatorRequestDTO dto) throws InterruptedException {
        String correlationId = dto.correlationId().equals("demo-header") ? UUID.randomUUID().toString() : dto.correlationId();

        int fixedDelayMs = dto.fixedDelaysMs();
        int jitterMs = dto.jitterMs();

        if(fixedDelayMs != 0 || jitterMs != 0){
            int randomDelayMs = ThreadLocalRandom.current().nextInt(0, jitterMs + 1);
            Thread.sleep(fixedDelayMs + randomDelayMs);
        }

        if(ErrorPercentageCalculator.shouldFail(dto.errorPercentage())){
            throw new IntentionalFailureException("Injected failure", dto.errorStatus());
        }

        return null;
    }

    public void setConcurrency(int concurrency) {
        executor.setMaximumPoolSize(concurrency);
        executor.setCorePoolSize(concurrency);
    }

    public int getConcurrency() {
        return executor.getCorePoolSize();
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
