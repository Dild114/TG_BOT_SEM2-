package app.api.bot.service.command;

import app.api.bot.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
public class BotCommandService {
  private final TelegramBot telegramBot;

  public BotCommandService(@Lazy TelegramBot telegramBot) {
    this.telegramBot = telegramBot;
  }

  @PostConstruct
  public void setBotCommands() {

    List<BotCommand> commands = List.of(
    new BotCommand("/start", "начать"),
    new BotCommand("/help", "помощь"),
    new BotCommand("/settings", "настройки")
    );

    try {
      telegramBot.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException e) {
      log.error("Ошибка при установке меню команд", e);
    }
  }
}
