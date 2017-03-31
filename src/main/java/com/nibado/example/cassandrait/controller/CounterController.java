package com.nibado.example.cassandrait.controller;

import com.nibado.example.cassandrait.base.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter")
public class CounterController {
    private final CounterRepository repository;

    @Autowired
    public CounterController(final CounterRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.POST)
    public void increment(@PathVariable final String key) {
        repository.increment(key);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable final String key) {
        return repository.get(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
