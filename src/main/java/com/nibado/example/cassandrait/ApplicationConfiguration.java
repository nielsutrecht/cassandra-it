package com.nibado.example.cassandrait;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApplicationConfiguration {
    @Value("${cassandra.host}")
    private String host;

    @Value("${cassandra.port}")
    private int port;

    @Bean
    public Session createSession() {
        return createSession(host, port);
    }

    public static Session createSession(String ip, int port) {
        Cluster cluster;

        cluster = Cluster.builder()
                .addContactPoint(ip)
                .withPort(port)
                .build();

        Session session = cluster.connect();

        session.execute("CREATE KEYSPACE IF NOT EXISTS cassandrait WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 }");
        session.execute("DROP TABLE IF EXISTS cassandrait.counter");
        session.execute("CREATE TABLE cassandrait.counter (key text, value counter, PRIMARY key(key));");

        return session;
    }
}
