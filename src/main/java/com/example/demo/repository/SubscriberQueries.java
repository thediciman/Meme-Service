package com.example.demo.repository;

public class SubscriberQueries {
    public static final String GET_ALL = "SELECT * FROM subscribers";
    public static final String GET_BY_EMAIL = "SELECT * FROM subscribers WHERE email = ?";
    public static final String DELETE = "DELETE FROM subscribers WHERE email = ?";
    public static final String SAVE = "INSERT INTO subscribers (email) VALUES (?)";
}