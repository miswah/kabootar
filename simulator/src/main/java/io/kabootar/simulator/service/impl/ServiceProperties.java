package io.kabootar.simulator.service.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties(prefix = "app.simulator")
public class ServiceProperties {
    private String serviceName;
    private String regionName;
    private String instanceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public ServiceProperties(String instanceName, String regionName, String serviceName) {
        this.instanceName = instanceName;
        this.regionName = regionName;
        this.serviceName = serviceName;
    }

    public ServiceProperties() {
    }
}