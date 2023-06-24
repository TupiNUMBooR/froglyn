package me.keller.froglyn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.Config;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

    private final RestTemplate restTemplate;
    private final Config config;

    public void sendMessage(String message) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://api.telegram.org")
            .path("/bot" + config.getTelegramBotToken() + "/sendMessage")
            .queryParam("chat_id", config.getTelegramChatId())
            .queryParam("text", message)
            .buildAndExpand().toUri();

        restTemplate.getForObject(uri, Object.class);
    }
}
