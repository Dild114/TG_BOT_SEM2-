package app.api.bot.service.command.stateCommand.category;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.StateCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.category.CategoryMessageService;
import app.api.bot.stubs.CategoryServiceStub;
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
  private final CategoryServiceStub categoryServiceStub; //TODO: заменить на categoryService

  @Override
  public boolean canHandle(long chatId) {
    String state = chatStateService.getState(chatId);
    return state != null && state.equals("awaiting_category_name_to_add");
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String text = message.getText();
    if (text.isBlank() || text.length() > 15 || !text.matches("^[a-zA-Z0-9а-яА-ЯёЁ]+$")) {
      messageSenderService.sendTextMessage(chatId, "❗\uFE0F Недопустимое название \"" + message.getText()
        + "\"\nНазвание категории должно быть единым словом длиной не более 15 символов");
    } else {
      //TODO: заменить на нормальный сервис
      categoryServiceStub.addCategory(message.getText());
      //TODO: List<CategoryDto> userCategories = categoryService.getCategories(chatId);
      categoryMessageService.updateCategoryMenuMessage(chatId, 1, categoryServiceStub.getCategories()); //TODO: заменить на userCategories
      messageSenderService.sendTextMessage(chatId, "☑\uFE0F Категория \"" + message.getText() + "\" успешно добавлена");
    }
    chatStateService.clearState(chatId);
  }
}
