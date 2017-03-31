package com.nibado.example.cassandrait.base;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class CounterIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Session session;

    private Mapper<Counter> mapper;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();

        mapper = new MappingManager(session).mapper(Counter.class);
    }

    @Test
    public void getAndIncrement() throws Exception {
        assertThat(countRows(session)).isZero();

        increment("foo");
        increment("bar");
        increment("foo");

        assertThat(countRows(session)).isEqualTo(2);

        get("foo", 2);
        get("bar",1);
    }

    private void increment(String key) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/counter/" + key))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void get(String key, int expected) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/counter/" + key))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is(expected)));
    }

    public static long countRows(Session session) {
        ResultSet set = session.execute("SELECT COUNT(*) from cassandrait.counter");

        return set.one().getLong(0);
    }
}
