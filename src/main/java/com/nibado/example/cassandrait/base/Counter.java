package com.nibado.example.cassandrait.base;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.Data;

@Data
@Table(keyspace = "cassandrait", name = "counter")
public class Counter {
    @PartitionKey
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private long value;
}
