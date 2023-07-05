package me.keller.froglyn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.dto.WeatherResponseDto;
import me.keller.froglyn.service.ScheduledWorkerService;
import me.keller.froglyn.service.TelegramService;
import me.keller.froglyn.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class ScheduledWorkerServiceTest extends AbstractTest {

    @Autowired
    private ScheduledWorkerService scheduledWorkerService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TelegramService telegramService;

    @MockBean
    private WeatherService weatherService;

    @Value("classpath:me.keller.froglyn/currentWeatherResponse.json")
    private Resource currentWeatherResponse;

    @Test
    @SneakyThrows
    void checkWeatherAndSend() {
        String currentWeatherResponseString = currentWeatherResponse.getContentAsString(StandardCharsets.UTF_8);
        WeatherResponseDto weatherDto = mapper.readValue(currentWeatherResponseString, WeatherResponseDto.class);

        when(weatherService.getCurrentWeather(anyDouble(), anyDouble())).thenReturn(weatherDto);
        doNothing().when(telegramService).sendMessage(any());

        scheduledWorkerService.checkWeatherAndSend();
    }
}
