package de.uniklinik.medic.pseudo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jonathan on 21.May.2020 . 6:13 PM
 */

@Configuration
@ConfigurationProperties(prefix = "server")
public class ConfigProperties {

    private String host;
    private String serviceId;


    public ConfigProperties() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
