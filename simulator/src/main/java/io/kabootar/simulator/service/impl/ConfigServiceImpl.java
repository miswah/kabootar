package io.kabootar.simulator.service.impl;

import io.kabootar.simulator.dto.request.ConfigRequestDTO;
import io.kabootar.simulator.enums.ConfigKey;
import io.kabootar.simulator.service.interfaces.ConfigService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConcurrentMap<ConfigKey, String> config = new ConcurrentHashMap<>();


    @Override
    public String get(ConfigKey key) {
        return config.get(key);
    }

    @Override
    public void put(ConfigKey key, String value) {
        config.compute(key, (k, v) -> value);
    }

    @Override
    public void put(ConfigRequestDTO dto) {
        if(dto.fixedDelaysMs() != null){
            this.put(ConfigKey.FIXEDDELAYSMS, String.valueOf(dto.fixedDelaysMs()));
        }

        if(dto.jitterMs() != null){
            this.put(ConfigKey.JITTERMS, String.valueOf(dto.jitterMs()));
        }

        if(dto.errorPercentage() != null){
            this.put(ConfigKey.ERRORPERCENTAGE, String.valueOf(dto.errorPercentage()));
        }

        if(dto.errorStatus() != null){
            this.put(ConfigKey.ERRORSTATUS, String.valueOf(dto.errorStatus()));
        }

        if(dto.errorStatus() != null){
            this.put(ConfigKey.MAXIMUMCONCURRENCY, String.valueOf(dto.errorStatus()));
        }

        this.put(ConfigKey.OUTAGE, String.valueOf(dto.outage()));
    }
}
