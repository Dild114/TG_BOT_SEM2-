package app.api.bot.service.command.stateCommand.category;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.category.CategoryMessageService;
import app.api.bot.stubs.exceptions.InvalidValueException;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(11)
@RequiredArgsConstructor
@Slf4j
public class AwaitingCategoryNameToAddHandler implements StateCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;
  private final CategoryMessageService categoryMessageService;
  private final CategoryService categoryService; //TODO: заменить на categoryService

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_category_name_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text == null || text.isBlank() || text.length() > 20 || text.trim().split("\\s+").length > 4 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ\\s]+$")) {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Недопустимое название \"" + message.getText()
        + "\"\nНазвание категории должно состоять из 1-4 слов и не более 20 символов (включая пробелы)");
      messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести название ещё раз:");
    } else {

      try {
        //TODO: заменить на нормальный сервис
        categoryService.addCategoryToUser(chatId, message.getText());
        //TODO: List<CategoryDto> userCategories = categoryService.getCategories(chatId);
        categoryMessageService.updateCategoryMenuMessage(chatId, 1, categoryService.getUserCategories(chatId)); //TODO: заменить на userCategories
        messageSenderService.sendTextMessage(chatId, "☑\uFE0F Категория \"" + message.getText() + "\" успешно добавлена");
        chatStateService.clearState(chatId);
      } catch (InvalidValueException e) {
        messageSenderService.sendTextMessage(chatId, "⚠\uFE0F Категория " + "\"" + message.getText() + "\"" + " уже была добавлена ранее");
        messageSenderService.sendTextMessage(chatId, "\uD83D\uDD04 Попробуйте ввести название ещё раз:");
      }
    }
  }
}
