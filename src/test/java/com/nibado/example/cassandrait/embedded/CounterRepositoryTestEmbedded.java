package com.nibado.example.cassandrait.embedded;

import com.datastax.driver.core.Session;
import com.nibado.example.cassandrait.base.CounterRepositoryTest;
import info.archinnov.achilles.internals.runtime.AbstractManagerFactory;
import info.archinnov.achilles.junit.AchillesTestResource;
import info.archinnov.achilles.junit.AchillesTestResourceBuilder;
import org.junit.Rule;

public class CounterRepositoryTestEmbedded extends CounterRepositoryTest {
    @Rule
    public AchillesTestResource<AbstractManagerFactory> resource = embeddedCassandra() ;

    public static AchillesTestResource<AbstractManagerFactory> embeddedCassandra() {
        return AchillesTestResourceBuilder
                .forJunit()
                .withScript("schema.cql")
                .createAndUseKeyspace("cassandrait")
                .build((cluster, statementsCache) -> null);
    }

    @Override
    public Session getSession() {
        return resource.getNativeSession();
    }
}
