package me.keller.froglyn.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.Config;
import me.keller.froglyn.dto.WeatherResponseDto;
import org.apache.commons.lang3.Range;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledWorkerService {
    //℃ or °C?

    private static final Range<Double> GOOD_TEMP = Range.between(15d, 30d);
    private static final boolean SEND_IF_NOT_WARN = true;

    private final WeatherService weatherService;
    private final TelegramService telegramService;
    private final Config config;

    @PostConstruct
    private void postConstruct() {
        if (config.isCheckAtStart()) {
            checkWeatherAndSend();
        }
    }

    @Scheduled(cron = "* 7 * * * *")
    public void checkWeatherAndSend() {
        WeatherResponseDto dto = weatherService.getCurrentWeather(config.getLat(), config.getLon());

        double temp = dto.getMain().getTemp();
        double feelsTemp = dto.getMain().getFeels_like();
        boolean warn = Stream.of(temp, feelsTemp)
            .anyMatch(Predicate.not(GOOD_TEMP::contains));
        boolean send = SEND_IF_NOT_WARN || warn;
        log.debug(format("temp %,.2f (feels %,.2f), warn %b, send %b", temp, feelsTemp, warn, send));

        if (!send) return;

        String weatherText = dto.getWeather().stream()
            .map(WeatherResponseDto.WeatherDto::getDescription)
            .collect(Collectors.joining(", "));
        String message = format("%stemp %,.2f°C (feels %,.2f°C), weather:\n%s",
            warn ? "warning!\n" : "", temp, feelsTemp, weatherText);

        telegramService.sendMessage(message);
    }
}
