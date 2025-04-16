package app.api.bot;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.BasicCommandHandler;
import app.api.bot.service.HandlerInterfaces.MenuCommandHandler;
import app.api.bot.service.HandlerInterfaces.StateCommandHandler;
import app.api.bot.service.UserStateService;
import app.api.bot.stubs.CategoryServiceStub;
import app.api.bot.stubs.SourceServiceStub;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class BotUpdateHandler {
  private final BotMessageService botMessageService;
  private final CategoryServiceStub categoryServiceStub;
  private final SourceServiceStub sourceServiceStub;
  private final UserStateService userStateService;
  List<MenuCommandHandler> menuCommandHandlers;
  List<BasicCommandHandler> basicCommandHandlers;
  List<StateCommandHandler> stateCommandHandlers;

  public void handleUpdate(Update update) {
    if (update.hasCallbackQuery()) {
      handleCallBackQuery(update.getCallbackQuery());
    } else if (update.hasMessage()) {
      log.info("Обновление содержит сообщение");
      Message message = update.getMessage();
      long chatId = message.getChatId();

      if (message.hasText()) {
        String text = message.getText();
        boolean used = false;

        for (MenuCommandHandler handler : menuCommandHandlers) {
          if (handler.canHandle(text)) {
            handler.handle(message);
            used = true;
            break;
          }
        }

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
              botMessageService.deleteLastBotMessage(chatId);
              handler.handle(message);
              used = true;
              break;
            }
          }
        }

        if (!used) {
          userStateService.clearState(chatId);
          botMessageService.sendMainMenuMessage(chatId);
        }

      } else {
        String responseText = "Пока я умею работать только с текстовыми запросами \uD83E\uDD17";
        botMessageService.sendMessage(chatId, responseText);
      }
      botMessageService.deleteMessage(chatId, message.getMessageId());
    }
  }

  private void handleCallBackQuery(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();
    int messageId = callbackQuery.getMessage().getMessageId();

    if (callbackData. equals("close_message")) {
      botMessageService.deleteMessage(chatId, messageId);

    } else if (callbackData.startsWith("page_category_")) {

      String[] parts = callbackData.split("_");
      int pageNum = Integer.parseInt(parts[2]);
      // TODO: заменить заглушки на нормальный сервис,
      // TODO: при этом в сервисе прописать, чтобы он возвращал именно названия категорий
      try {
        botMessageService.updateCategoryMenuMessage(chatId, pageNum, categoryServiceStub.getCategories());
      } catch (TelegramApiRequestException e) {
        log.info("Что-то не так при обновлении списка категорий в чате {}", chatId);
      }

    } else if (callbackData.startsWith("change_category_status_")) {

      String[] parts = callbackData.split("_");
      String categoryName = parts[3];
      int pageNum = Integer.parseInt(parts[4]);
      categoryServiceStub.changeStatus(categoryName);
      try {
        botMessageService.updateCategoryMenuMessage(chatId, pageNum, categoryServiceStub.getCategories());
      } catch (TelegramApiRequestException e) {
        log.info("Что-то не так при обновлении списка категорий в чате {}", chatId);
      }

    } else if (callbackData.startsWith("page_source_")) {
      String[] parts = callbackData.split("_");
      int pageNum = Integer.parseInt(parts[2]);
      try {
        botMessageService.updateSourceMenuMessage(chatId, pageNum, sourceServiceStub.getSources(), userStateService.getTempViewMode(chatId));
      } catch (TelegramApiRequestException e) {
        log.info("Что-то не так при обновлении списка источников в чате {}", chatId);
      }

    } else if (callbackData.startsWith("change_source_status_")) {
      String[] parts = callbackData.split("_");
      String sourceName = parts[3];
      int pageNum = Integer.parseInt(parts[4]);
      sourceServiceStub.changeStatus(sourceName);
      try {
        botMessageService.updateSourceMenuMessage(chatId, pageNum, sourceServiceStub.getSources(), userStateService.getTempViewMode(chatId));
      } catch (TelegramApiRequestException e) {
        log.info("Что-то не так при обновлении списка источников в чате {}", chatId, e);
      }
    } else if (callbackData.startsWith("change_source_view_")) {
      String[] parts = callbackData.split("_");
      int pageNum = Integer.parseInt(parts[4]);
      String newView = parts[3];
      userStateService.setTempViewMode(chatId, newView);
      try {
        botMessageService.updateSourceMenuMessage(chatId, pageNum, sourceServiceStub.getSources(), newView);
      } catch (TelegramApiRequestException e) {
        log.info("Что-то не так при обновлении списка источников в чате {}", chatId, e);
      }
    }
  }
}
