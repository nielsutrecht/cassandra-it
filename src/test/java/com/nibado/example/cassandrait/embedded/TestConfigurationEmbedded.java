package com.nibado.example.cassandrait.embedded;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static info.archinnov.achilles.embedded.CassandraEmbeddedConfigParameters.CLUSTER_NAME;

@Configuration
public class TestConfigurationEmbedded {
    @Bean
    @Primary
    public Session createSession() {
        final Cluster cluster = CassandraEmbeddedServerBuilder
                .builder()
                .cleanDataFilesAtStartup(true)
                .withKeyspaceName("cassandrait")
                .withScript("schema.cql")
                .withClusterName(CLUSTER_NAME)
                .buildNativeCluster();

        return cluster.connect("cassandrait");
    }
}
