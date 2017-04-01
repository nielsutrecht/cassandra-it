package com.nibado.example.cassandrait.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CounterRepository {
    private final Session session;
    private final PreparedStatement incrementStatement;
    private final Mapper<Counter> mapper;

    @Autowired
    public CounterRepository(final Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(Counter.class);
        this.incrementStatement = session.prepare("UPDATE cassandrait.counter SET value = value + 1 WHERE key=?");
    }

    public void increment(final String key) {
        session.execute(incrementStatement.bind(key));
    }

    public Optional<Counter> get(final String key) {
        return Optional.ofNullable(mapper.get(key));
    }
}
