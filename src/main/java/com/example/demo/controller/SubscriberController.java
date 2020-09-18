package com.example.demo.controller;

import com.example.demo.model.Subscriber;
import com.example.demo.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<HttpStatus> subscribe(@RequestParam String email) {
        log.info("POST request to subscribe email " + email);
        try {
            subscriberService.save(new Subscriber(email));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<HttpStatus> unsubscribe(@RequestParam String email) {
        log.info("POST request to unsubscribe email " + email);
        try {
            subscriberService.delete(new Subscriber(email));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<Subscriber>> getAll() {
        log.info("GET request for all subscribers");
        return new ResponseEntity<>(subscriberService.getAll(), HttpStatus.OK);
    }

}