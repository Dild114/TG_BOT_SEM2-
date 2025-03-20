package app.api.bot;

import app.api.bot.config.BotConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
  private final BotConfig botConfig;
  private final BotUpdateHandler botUpdateHandler;

  public TelegramBot(BotConfig botConfig, @Lazy BotUpdateHandler botUpdateHandler) {
    super(botConfig.getBotToken());
    this.botConfig = botConfig;
    this.botUpdateHandler = botUpdateHandler;
  }

  @Override
  public void onUpdateReceived(Update update) {
    botUpdateHandler.handleUpdate(update);
  }

  @Override
  public String getBotUsername() {
    return botConfig.getBotName();
  }

  @PostConstruct
  public void init() {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(this);
    } catch (Exception e) {
      log.warn("Ошибка регистрации бота в tg api {}", e.getMessage());
    }
  }
}
