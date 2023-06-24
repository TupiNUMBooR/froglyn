package me.keller.froglyn;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Config {

    @Value("${froglyn.coords.lat}")
    double lat;

    @Value("${froglyn.coords.lon}")
    double lon;

    @Value("${froglyn.openWeatherMap.api.key}")
    String openWeatherMapApiKey;

    @Value("${froglyn.telegram.bot.token}")
    String telegramBotToken;

    @Value("${froglyn.telegram.chat.id}")
    long telegramChatId;

    @Value("${froglyn.checkAtStart}")
    boolean checkAtStart;
}
