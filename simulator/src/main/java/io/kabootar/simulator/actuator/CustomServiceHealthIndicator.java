package io.kabootar.simulator.actuator;

import io.kabootar.simulator.enums.ConfigKey;
import io.kabootar.simulator.service.interfaces.ConfigService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomServiceHealthIndicator  implements HealthIndicator {
    private final ConfigService configService;

    @Autowired
    public CustomServiceHealthIndicator(ConfigService configService){
        this.configService = configService;
    }

    @Override
    public @Nullable Health health() {
        boolean outage = this.configService.get(ConfigKey.OUTAGE) != null ? Boolean.parseBoolean(this.configService.get(ConfigKey.OUTAGE)) : false;
        double errorPercentage = this.configService.get(ConfigKey.ERRORPERCENTAGE) != null ? Double.parseDouble(this.configService.get(ConfigKey.ERRORPERCENTAGE)) : 0.0;
        boolean isServiceHealthy = outage || errorPercentage != 0.0;


        if (!isServiceHealthy) {
            // Return a completely custom status code instead of DOWN
            return Health.down()
                    .withDetail("reason", "Down due to outage")
                    .build();
        }

        return Health.up().build();
    }
}
