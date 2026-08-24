package io.kabootar.simulator.controller;


import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.service.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulator/api/v1")
public class SimulatorController {

    private final SimulatorService service;

    @Autowired
    public SimulatorController(SimulatorService service){
        this.service = service;
    }


    @GetMapping("/instance")
    public ResponseEntity<SimulatorResponseDTO> getInstance(@RequestParam Integer fixedDelayMs, @RequestParam Integer jitterMs,
                                                            @RequestParam Integer errorPercentage, @RequestParam Integer errorStatus,
                                                            @RequestParam boolean outage, @RequestParam Integer maximumConcurrency, @RequestHeader("X-Correlation-ID") String correlationId){
        SimulatorRequestDTO dto = new SimulatorRequestDTO(fixedDelayMs, jitterMs, errorPercentage, errorStatus, outage, maximumConcurrency, correlationId);
        return ResponseEntity.ok(this.service.getInstance(dto));
    }

}