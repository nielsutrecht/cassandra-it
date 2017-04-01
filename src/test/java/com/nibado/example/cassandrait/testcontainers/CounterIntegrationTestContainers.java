package com.nibado.example.cassandrait.testcontainers;

import com.nibado.example.cassandrait.ExampleApplication;
import com.nibado.example.cassandrait.base.CounterIntegrationTest;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.GenericContainer;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ExampleApplication.class, TestConfigurationTestContainers.class})
@ContextConfiguration(initializers = CounterIntegrationTestContainers.Initializer.class)
@EnableConfigurationProperties
public class CounterIntegrationTestContainers extends CounterIntegrationTest {
    @ClassRule
    public static GenericContainer cassandra =
            new GenericContainer("cassandra:3")
                    .withExposedPorts(9042);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            EnvironmentTestUtils.addEnvironment("testcontainers", configurableApplicationContext.getEnvironment(),
                    "cassandra.host=" + cassandra.getContainerIpAddress(),
                    "cassandra.port=" + cassandra.getMappedPort(9042)
            );
        }
    }
}
