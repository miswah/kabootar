package io.kabootar.simulator.service.impl;

import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.enums.ConfigKey;
import io.kabootar.simulator.exceptions.IntentionalFailureException;
import io.kabootar.simulator.exceptions.QueueFullException;
import io.kabootar.simulator.service.interfaces.ConfigService;
import io.kabootar.simulator.service.interfaces.SimulatorService;
import io.kabootar.simulator.utilities.ErrorPercentageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SimulatorServiceImpl implements SimulatorService {
    private final ThreadPoolExecutor executor;

    private final ConfigService configService;
    private final ServiceProperties propertyService;

    @Autowired
    public SimulatorServiceImpl(ConfigService configService, ServiceProperties propertyService){
        this.configService = configService;
        this.propertyService = propertyService;

        int maxConcurrency = 5; // should be overridden from properties??
        String MAXIMUMCONCURRENCY = this.configService.get(ConfigKey.MAXIMUMCONCURRENCY);
        int queueSize = MAXIMUMCONCURRENCY != null ? Integer.parseInt(MAXIMUMCONCURRENCY) : 99;

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
    public Future<SimulatorResponseDTO> submit(String correctionId) {
        try {
            return executor.submit(() -> {
                return this.getInstance(correctionId);
            });
        } catch (RejectedExecutionException e) {
            throw new QueueFullException(
                    "Simulator is busy. Queue is full."
            );
        }
    }


    private SimulatorResponseDTO getInstance(String correlationIdX) throws InterruptedException {
        String correlationId = correlationIdX.equals("demo-header") ? UUID.randomUUID().toString() : correlationIdX;

        int fixedDelayMs = this.configService.get(ConfigKey.FIXEDDELAYSMS) != null ? Integer.parseInt(this.configService.get(ConfigKey.FIXEDDELAYSMS)) : 0;

        int jitterMs = this.configService.get(ConfigKey.JITTERMS) != null ? Integer.parseInt(this.configService.get(ConfigKey.JITTERMS)) : 0;

        if(fixedDelayMs != 0 || jitterMs != 0){
            int randomDelayMs = ThreadLocalRandom.current().nextInt(0, jitterMs + 1);
            Thread.sleep(fixedDelayMs + randomDelayMs);
        }

        double errorRate = this.configService.get(ConfigKey.ERRORPERCENTAGE) != null ? Double.parseDouble(this.configService.get(ConfigKey.ERRORPERCENTAGE)) : 0.0;
        if(ErrorPercentageCalculator.shouldFail(errorRate)){
            throw new IntentionalFailureException("Injected failure", (int) errorRate);
        }
        return new SimulatorResponseDTO(this.propertyService.getServiceName(), this.propertyService.getRegionName(),
                this.propertyService.getInstanceName(), correlationId, LocalDateTime.now().toString());
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
