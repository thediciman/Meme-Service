package com.example.demo.mapper;

import com.example.demo.model.Subscriber;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriberMapper implements RowMapper<Subscriber> {

    @Override
    public Subscriber mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Subscriber(resultSet.getString("email"));
    }

}