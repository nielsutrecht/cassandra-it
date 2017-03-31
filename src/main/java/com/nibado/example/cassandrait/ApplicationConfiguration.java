package com.nibado.example.cassandrait;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    @Bean
    public Session createSession() {
        log.info("ApplicationConfiguration.createSession");
        Cluster cluster;

        cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();

        Session session = cluster.connect();

        session.execute("CREATE KEYSPACE IF NOT EXISTS cassandrait WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
        session.execute("DROP TABLE IF EXISTS cassandrait.counter");
        session.execute("CREATE TABLE cassandrait.counter (key text, value counter, PRIMARY key(key));");

        return session;
    }
}
