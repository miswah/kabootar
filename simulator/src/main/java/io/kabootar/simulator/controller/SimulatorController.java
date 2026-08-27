package io.kabootar.simulator.controller;


import io.kabootar.simulator.dto.request.ConfigRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.enums.ConfigKey;
import io.kabootar.simulator.service.interfaces.ConfigService;
import io.kabootar.simulator.service.interfaces.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/v1")
public class SimulatorController {

    private final SimulatorService simulatorService;
    private final ConfigService configService;

    @Autowired
    public SimulatorController(SimulatorService service, ConfigService configService){
        this.simulatorService = service;
        this.configService = configService;
    }


    @PostMapping("/config")
    public ResponseEntity<String> updateConfig(@RequestBody ConfigRequestDTO dto){
        this.configService.put(dto);

        return ResponseEntity.ok("Config Updated");
    }

    @GetMapping("/instance")
    public ResponseEntity<SimulatorResponseDTO> getInstance(@RequestHeader(value="X-Correlation-ID", defaultValue = "demo-header") String correlationId) {

        if(Boolean.parseBoolean(this.configService.get(ConfigKey.OUTAGE))){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        try {
            Future<SimulatorResponseDTO> future = this.simulatorService.submit(correlationId);
            SimulatorResponseDTO result = future.get();

            return ResponseEntity.ok(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .build();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        } catch (ExecutionException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}