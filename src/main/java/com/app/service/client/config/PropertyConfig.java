package com.app.service.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Data
@Configuration
public class PropertyConfig {
    @Autowired
    private Environment environment;

    public String getProperty(String nameProp) {
        return environment.getProperty(nameProp);
    }
}
