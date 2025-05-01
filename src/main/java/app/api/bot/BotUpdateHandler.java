package app.api.bot;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.command.handlerInterfaces.MenuCommandHandler;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.mainMenu.MainMenuMessageService;
import app.api.bot.stubs.article.ArticleServiceStub;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class BotUpdateHandler {
  private final MessageSenderService messageSenderService;
  private final ChatStateService chatStateService;
  private final MainMenuMessageService mainMenuMessageService;
  private final ArticleServiceStub articleServiceStub;
  List<MenuCommandHandler> menuCommandHandlers;
  List<BasicCommandHandler> basicCommandHandlers;
  List<StateCommandHandler> stateCommandHandlers;
  List<CallbackCommandHandler> callbackCommandHandlers;

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

      if (chatStateService.getState(chatId) != null && chatStateService.getState(chatId).equals("getting_articles")) {
        articleServiceStub.deleteUnneededUserArticles(chatId);
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
        chatStateService.clearState(chatId);
        mainMenuMessageService.sendMainMenuMessage(chatId);
      }
    }
  }
}
