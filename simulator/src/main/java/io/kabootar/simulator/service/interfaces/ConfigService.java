package io.kabootar.simulator.service.interfaces;

import io.kabootar.simulator.dto.request.ConfigRequestDTO;
import io.kabootar.simulator.enums.ConfigKey;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService {
    String get(ConfigKey key);
    void put(ConfigKey key, String value);
    void put(ConfigRequestDTO dto);
}
