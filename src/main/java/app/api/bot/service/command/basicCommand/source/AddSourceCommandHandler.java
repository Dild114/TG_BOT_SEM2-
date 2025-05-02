package app.api.bot.service.command.basicCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(8)
@RequiredArgsConstructor
public class AddSourceCommandHandler implements BasicCommandHandler {
  private final UserService userService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "➕ Добавить источник".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD➕ Введите название источника:");
    userService.setState(chatId, "awaiting_source_name_to_add");
  }
}
