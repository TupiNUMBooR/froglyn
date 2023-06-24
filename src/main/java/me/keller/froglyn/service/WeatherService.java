package me.keller.froglyn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.Config;
import me.keller.froglyn.dto.WeatherResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final Config config;

    /**
     * <a href="https://openweathermap.org/current">api</a>
     */
    @SneakyThrows
    public WeatherResponseDto getCurrentWeather(double lat, double lon) {
        log.info(format("get weather for %,.2f, %,.2f", lat, lon));

        URI uri = UriComponentsBuilder
            .fromHttpUrl("https://api.openweathermap.org")
            .path("/data/2.5/weather")
            .queryParam("units", "metric")
            .queryParam("lat", lat)
            .queryParam("lon", lon)
            .queryParam("appid", config.getOpenWeatherMapApiKey())
            .buildAndExpand().toUri();

        WeatherResponseDto response = restTemplate.getForObject(uri, WeatherResponseDto.class);
        log.debug("get weather: {}", mapper.writer().writeValueAsString(response));

        return response;
    }
}
