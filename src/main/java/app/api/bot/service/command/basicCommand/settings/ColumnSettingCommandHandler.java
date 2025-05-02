package app.api.bot.service.command.basicCommand.settings;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(4)
@RequiredArgsConstructor
public class ColumnSettingCommandHandler implements BasicCommandHandler {
  private final UserService userService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "Изменить кол-во элементов, отображаемых на одной странице".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDD22 Введите новое значение:");
    userService.setState(chatId, "awaiting_new_column_height");
  }
}
