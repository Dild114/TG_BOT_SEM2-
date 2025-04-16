package app.api.bot.service.stateCommand.category;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.StateCommandHandler;
import app.api.bot.service.UserStateService;
import app.api.bot.stubs.CategoryServiceStub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
@Order(12)
@RequiredArgsConstructor
@Slf4j
public class AwaitingCategoryNameToDeleteHandler implements StateCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;
  private final CategoryServiceStub categoryServiceStub; //TODO: заменить на categoryService

  @Override
  public boolean canHandle(long chatId) {
    String state = userStateService.getState(chatId);
    return state != null && state.equals("awaiting_category_name_to_delete");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Недопустимое название категории + \"" + message.getText() + "\"\nНазвание категории должно быть единым словом длиной не более 15 символов");
    } else {
      //TODO: заменить на нормальный сервис
      categoryServiceStub.deleteCategory(message.getText());
      try {
        //TODO: List<CategoryDto> userCategories = categoryService.getCategories(chatId);
        botMessageService.updateCategoryMenuMessage(chatId, 1, categoryServiceStub.getCategories());
        botMessageService.sendMessage(chatId, "☑\uFE0F Категория \"" + message.getText() + "\" успешно удалена");
      } catch (TelegramApiRequestException e) {
        botMessageService.sendMessage(chatId, "❗\uFE0F Категория \"" + message.getText() + "\" не найдена");
      }
    }
    userStateService.clearState(chatId);
  }
}
