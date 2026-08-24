package io.kabootar.simulator.controller;


import io.kabootar.simulator.dto.request.SimulatorRequestDTO;
import io.kabootar.simulator.dto.response.SimulatorResponseDTO;
import io.kabootar.simulator.service.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SimulatorController {

    private final SimulatorService service;

    @Autowired
    public SimulatorController(SimulatorService service){
        this.service = service;
    }


    @GetMapping("/instance")
    public ResponseEntity<SimulatorResponseDTO> getInstance(@RequestParam(defaultValue = "0") Integer fixedDelayMs, @RequestParam(defaultValue = "0") Integer jitterMs,
                                                            @RequestParam(defaultValue = "0.0") Double errorPercentage, @RequestParam(defaultValue = "0") Integer errorStatus,
                                                            @RequestParam(defaultValue = "false") boolean outage, @RequestParam(defaultValue = "0") Integer maximumConcurrency, @RequestHeader(value="X-Correlation-ID", defaultValue = "demo-header") String correlationId) throws InterruptedException {
        SimulatorRequestDTO dto = new SimulatorRequestDTO(fixedDelayMs, jitterMs, errorPercentage, errorStatus, outage, maximumConcurrency, correlationId);
        return ResponseEntity.ok(this.service.getInstance(dto));
    }

}