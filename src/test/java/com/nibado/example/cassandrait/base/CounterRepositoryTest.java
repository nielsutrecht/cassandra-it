package com.nibado.example.cassandrait.base;

import com.datastax.driver.core.Session;
import com.nibado.example.cassandrait.repository.CounterRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CounterRepositoryTest {
    private Session session;

    protected CounterRepository repository;

    public abstract Session getSession();

    @Before
    public void setup() {
        repository = new CounterRepository(getSession());
    }

    @Test
    public void getAndIncrement() {
        assertThat(repository.get("foo")).isEmpty();

        repository.increment("foo");
        repository.increment("foo");
        repository.increment("bar");

        assertThat(repository.get("foo").get().getValue()).isEqualTo(2);
        assertThat(repository.get("bar").get().getValue()).isEqualTo(1L);
    }
}
