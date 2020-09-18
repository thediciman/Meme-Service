package com.example.demo.repository;

import com.example.demo.mapper.SubscriberMapper;
import com.example.demo.model.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SubscriberMapper subscriberMapper;

    @Autowired
    public SubscriberRepository(JdbcTemplate jdbcTemplate, SubscriberMapper subscriberMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.subscriberMapper = subscriberMapper;
    }

    public List<Subscriber> getAll() {
        return jdbcTemplate.query(SubscriberQueries.GET_ALL, subscriberMapper);
    }

    public Optional<Subscriber> getByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SubscriberQueries.GET_BY_EMAIL, subscriberMapper, email));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void save(Subscriber subscriber) {
        if (getByEmail(subscriber.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Subscriber already exists.");
        }

        jdbcTemplate.update(SubscriberQueries.SAVE, subscriber.getEmail());
    }

    public void delete(Subscriber subscriber) {
        if (getByEmail(subscriber.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Subscriber does not exist.");
        }

        jdbcTemplate.update(SubscriberQueries.DELETE, subscriber.getEmail());
    }

}