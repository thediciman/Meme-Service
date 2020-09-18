package com.example.demo.service;

import com.example.demo.model.Subscriber;
import com.example.demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final MemeService memeService;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, MemeService memeService) {
        this.subscriberRepository = subscriberRepository;
        this.memeService = memeService;
    }

    public void save(Subscriber subscriber) {
        subscriberRepository.save(subscriber);
    }

    public void delete(Subscriber subscriber) {
        subscriberRepository.delete(subscriber);
    }

    public Optional<Subscriber> getByEmail(String email) {
        return subscriberRepository.getByEmail(email);
    }

    public List<Subscriber> getAll() {
        return subscriberRepository.getAll();
    }

}