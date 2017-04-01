package com.nibado.example.cassandrait.testcontainers;

import com.datastax.driver.core.Session;
import com.nibado.example.cassandrait.ApplicationConfiguration;
import com.nibado.example.cassandrait.base.CounterRepositoryTest;
import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;

public class CounterRepositoryTestContainers extends CounterRepositoryTest {
    @ClassRule
    public static GenericContainer cassandra =
            new GenericContainer("cassandra:3")
                    .withExposedPorts(9042);

    @Override
    public Session getSession() {
        return ApplicationConfiguration.createSession(cassandra.getContainerIpAddress(), cassandra.getMappedPort(9042));
    }
}
