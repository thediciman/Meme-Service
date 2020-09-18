package com.example.demo.service;

import com.example.demo.repository.SubscriberRepository;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class MemeService {

    private static final String MEME_API_URL = "https://meme-api.herokuapp.com/gimme";

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private EmailService emailService;

    private String getMemeUrl() {
        try {
            URL url = new URL(MEME_API_URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            JSONObject responseJson = JSONObjectUtils.parse(content.toString());

            return responseJson.get("url").toString();
        } catch (IOException | ParseException ex) {
            return "memegenerator.net/img/instances/56745550.jpg";
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendMeme() {
        log.info("Sending memes");

        subscriberRepository.getAll().forEach(subscriber -> {
            try {
                String memeUrl = getMemeUrl();

                MimeMessage mimeMessage = emailService.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

                String content = "<img src=" + memeUrl + " alt=\"meme\">";
                helper.setText(content, true);
                helper.setTo(subscriber.getEmail());
                helper.setSubject("Here's your hourly meme!");

                emailService.sendEmail(mimeMessage);
            } catch (Exception ex) {
                log.info("Failed to send meme to " + subscriber.getEmail());
            }
        });
    }

}