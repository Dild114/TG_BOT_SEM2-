package app.api.bot.service.command.basicCommand.category;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.MessageSenderService;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(6)
@RequiredArgsConstructor
public class AddCategoryCommandHandler implements BasicCommandHandler {
  private final UserService userService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "➕ Добавить категорию".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD➕ Введите название категории:");
    userService.setState(chatId, "awaiting_category_name_to_add");
  }
}
