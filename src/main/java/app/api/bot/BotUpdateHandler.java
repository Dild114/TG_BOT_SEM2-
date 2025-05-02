package app.api.bot;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.command.handlerInterfaces.MenuCommandHandler;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.mainMenu.MainMenuMessageService;
import app.api.service.*;
import jakarta.annotation.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotUpdateHandler {
  private final MessageSenderService messageSenderService;
  private final UserService userService;
  private final MainMenuMessageService mainMenuMessageService;
  private final ArticleService articleService;

  private final List<MenuCommandHandler> menuCommandHandlers;
  private final List<BasicCommandHandler> basicCommandHandlers;
  private final List<StateCommandHandler> stateCommandHandlers;
  private final List<CallbackCommandHandler> callbackCommandHandlers;

  private static long startupTime;

  @PostConstruct
  private void init() {
    startupTime = System.currentTimeMillis() / 1000;
  }

  public void handleUpdate(Update update) {
    if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      String callbackData = callbackQuery.getData();

      for (CallbackCommandHandler handler : callbackCommandHandlers) {
        if (handler.canHandle(callbackData)) {
          handler.handle(callbackQuery);
          break;
        }
      }
    } else if (update.hasMessage()) {
      log.info("Обновление содержит сообщение");
      Message message = update.getMessage();
      long chatId = message.getChatId();

      if (update.getMessage().getDate() < startupTime) {
        messageSenderService.deleteMessage(chatId, message.getMessageId());
        return;
      }

      if ("getting_articles".equals(userService.getState(chatId))) {
        articleService.deleteUnneededUserArticles(chatId);
      }

      boolean used = false;

      if (message.hasText()) {
        String text = message.getText();

        for (MenuCommandHandler handler : menuCommandHandlers) {
          if (handler.canHandle(text)) {
            handler.handle(message);
            used = true;
            break;
          }
        }
        messageSenderService.deleteMessage(chatId, message.getMessageId());

        if (!used) {
          for (BasicCommandHandler handler : basicCommandHandlers) {
            if (handler.canHandle(text)) {
              handler.handle(message);
              used = true;
              break;
            }
          }
        }

        if (!used) {
          for (StateCommandHandler handler : stateCommandHandlers) {
            if (handler.canHandle(chatId)) {
              messageSenderService.deleteLastBotMessage(chatId);
              handler.handle(message);
              used = true;
              break;
            }
          }
        }
      }
      if (!used) {
        userService.clearState(chatId);
        mainMenuMessageService.sendMainMenuMessage(chatId);
      }
    }
  }
}