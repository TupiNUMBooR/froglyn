package me.keller.froglyn;

import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.service.ScheduledWorkerService;
import me.keller.froglyn.service.TelegramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
@ActiveProfiles("test")
class FroglynApplicationTests {

    @Autowired
    ScheduledWorkerService scheduledWorkerService;

    @Autowired
    TelegramService telegramService;

    @Test
    void contextLoads() {
    }

    @Test
    void telegram() {
        telegramService.sendMessage("Hello World!");
    }

    @Test
    void ok() {
        scheduledWorkerService.checkWeatherAndSend();
    }
}
