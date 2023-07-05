package me.keller.froglyn;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.keller.froglyn.dto.WeatherResponseDto;
import me.keller.froglyn.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Slf4j
class WeatherServiceTest extends AbstractTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper mapper;

    @Value("classpath:me.keller.froglyn/currentWeatherResponse.json")
    private Resource currentWeatherResponse;

    @Test
    @SneakyThrows
    void getCurrentWeather() {
        String currentWeatherResponseString = currentWeatherResponse.getContentAsString(StandardCharsets.UTF_8);

        mockServer.expect(
            ExpectedCount.once(),
            requestTo(matchesPattern("https://api.openweathermap.org/data/2.5/weather.*"))
        ).andRespond(
            withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(currentWeatherResponseString)
        );

        WeatherResponseDto actual = weatherService.getCurrentWeather(48, 135);
        WeatherResponseDto expected = mapper.readValue(currentWeatherResponseString, WeatherResponseDto.class);
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
