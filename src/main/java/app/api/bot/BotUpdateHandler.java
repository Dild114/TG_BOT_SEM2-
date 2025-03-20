package app.api.bot;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.InlineKeyboardFactory;
import app.api.bot.stubs.CategoryServiceStub;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@AllArgsConstructor
public class BotUpdateHandler {
  private final BotMessageService botMessageService;
  private final CategoryServiceStub categoryServiceStub;
  private final InlineKeyboardFactory inlineKeyboardFactory;
  private final ConcurrentHashMap<Long, String> userState = new ConcurrentHashMap<>();

  public void handleUpdate(Update update) {
    if (update.hasCallbackQuery()) {
      handleCallBackQuery(update.getCallbackQuery());
    } else if (update.hasMessage()) {
      log.info("Обновление содержит сообщение");
      Message message = update.getMessage();
      long chatId = message.getChatId();

      if (message.hasText()) {
        switch (message.getText()) {

          case "/start" -> {
            String userName = message.getFrom().getFirstName();
            userState.remove(chatId);
            botMessageService.sendWelcomeMessage(chatId, userName);
          }

          case "/help", "ℹ️ Инструкция" -> {
            botMessageService.sendHelpMessage(chatId);
          }

          case "/settings" -> {
            userState.remove(chatId);
            botMessageService.sendSettingsMessage(chatId);
          }

          case "Изменить кол-во элементов, отображаемых на одной странице" -> {
            if (userState.get(chatId) != null) {
              botMessageService.deleteLastBotMessage(chatId);
            }
            botMessageService.sendMessage(chatId, "\uD83D\uDD22 Введите новое значение:");
            userState.put(chatId, "awaiting_new_column_height");
          }

          case "\uD83D\uDCD1 Категории" -> {
            // TODO: заменить заглушки на нормальный сервис,
            // TODO: при этом в сервисе прописать, чтобы он возвращал именно названия категорий
            botMessageService.sendCategoryMenuMessage(chatId, categoryServiceStub.getCategories());
          }

          case "➕ Добавить категорию" -> {
            if (userState.get(chatId) != null) {
              botMessageService.deleteLastBotMessage(chatId);
            }
            botMessageService.sendMessage(chatId, "\uD83D\uDCDD➕ Введите название категории:");
            userState.put(chatId, "awaiting_category_name_to_add");
          }

          case "\uD83D\uDDD1 Удалить категорию" -> {
            if (userState.get(chatId) != null) {
              botMessageService.deleteLastBotMessage(chatId);
            }
            botMessageService.sendMessage(chatId, "\uD83D\uDCDD➖ Введите название категории:");
            userState.put(chatId, "awaiting_category_name_to_delete");
          }

          case "\uD83C\uDF10 Источники" -> {
            botMessageService.sendSourceMenuMassage(chatId);
          }

          case "↩ Главная" -> {
            botMessageService.sendMainMenuMessage(chatId);
            userState.remove(chatId);
          }

          default -> {
            if (userState.get(chatId) != null) {
              handleUserStateRequests(message);
            } else {
              botMessageService.sendMainMenuMessage(chatId);
            }
            userState.remove(chatId);
          }
        }
        botMessageService.deleteMessage(chatId, message.getMessageId());
      } else {
        String responseText = "Пока я умею работать только с текстовыми запросами \uD83E\uDD17";
        botMessageService.sendMessage(chatId, responseText);
      }
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
    }
  }

  private void handleUserStateRequests(Message message) {
    long chatId = message.getChatId();
    String userStatus = userState.get(chatId);
    String text = message.getText();
    botMessageService.deleteLastBotMessage(chatId);
    if (userStatus.equals("awaiting_category_name_to_add")) {
      if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Недопустимое название \"" + message.getText() + "\"\nНазвание категории должно быть единым словом длиной не более 15 символов");
      } else {
        categoryServiceStub.addCategory(message.getText());
        try {
          botMessageService.updateCategoryMenuMessage(chatId, 1, categoryServiceStub.getCategories());
          botMessageService.sendMessage(chatId, "☑\uFE0F Категория \"" + message.getText() + "\" успешно добавлена");
        } catch (TelegramApiRequestException e) {
          botMessageService.sendMessage(chatId, "❗\uFE0F Категория \"" + message.getText() + "\" добавлена ранее");
          log.error(e.getMessage());
        }
      }


    } else if (userStatus.equals("awaiting_category_name_to_delete")) {
      if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Недопустимое название категории + \"" + message.getText() + "\"\nНазвание категории должно быть единым словом длиной не более 15 символов");
      } else {
        categoryServiceStub.deleteCategory(message.getText());
        try {
          botMessageService.updateCategoryMenuMessage(chatId, 1, categoryServiceStub.getCategories());
          botMessageService.sendMessage(chatId, "☑\uFE0F Категория \"" + message.getText() + "\" успешно удалена");
        } catch (TelegramApiRequestException e) {
          botMessageService.sendMessage(chatId, "❗\uFE0F Категория \"" + message.getText() + "\" не найдена");
        }
      }

    } else if (userStatus.equals("awaiting_new_column_height")) {
      if (text.length() <= 10 && text.matches("\\d+") && Integer.parseInt(text) > 0) {
        int newSize = Integer.parseInt(text);
        inlineKeyboardFactory.setPageSize(newSize);
        botMessageService.sendMessage(chatId, "☑\uFE0F Новая высота столбца \"" + message.getText() + "\" успешно установлена");
      } else {
        botMessageService.sendMessage(chatId, "❗\uFE0F Значение \"" + message.getText() + "\" неприемлемо. Высота столбца должна быть натуральным числом меньшим 10^9");
      }
    }
  }
}
