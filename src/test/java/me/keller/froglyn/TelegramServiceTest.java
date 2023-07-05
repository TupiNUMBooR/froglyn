package me.keller.froglyn;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.service.TelegramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Slf4j
class TelegramServiceTest extends AbstractTest {

    @Autowired
    private TelegramService telegramService;

    @Value("classpath:me.keller.froglyn/telegramResponse.json")
    private Resource telegramResponse;

    @Test
    @SneakyThrows
    void sendMessage() {
        String currentWeatherResponseString = telegramResponse.getContentAsString(StandardCharsets.UTF_8);

        mockServer.expect(
            ExpectedCount.once(),
            requestTo(matchesPattern("https://api.telegram.org/bot.*/sendMessage.*"))
        ).andRespond(
            withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(currentWeatherResponseString)
        );

        telegramService.sendMessage("ololo");
    }
}
