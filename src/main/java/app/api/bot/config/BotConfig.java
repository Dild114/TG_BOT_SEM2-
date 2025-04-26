package app.api.bot.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@Getter
public class BotConfig {
  @Value("${telegram.bot.name}")
  private String botName;

  @Value("${telegram.bot.token}")
  private String botToken;
}