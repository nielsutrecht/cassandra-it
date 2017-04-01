package com.nibado.example.cassandrait.testcontainers;

import com.datastax.driver.core.Session;
import com.nibado.example.cassandrait.ApplicationConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Slf4j
public class TestConfigurationTestContainers {
    @Value("${cassandra.host}")
    private String host;

    @Value("${cassandra.port}")
    private int port;

    @Bean
    @Primary
    public Session createSession() {
        return ApplicationConfiguration.createSession(host, port);
    }
}
