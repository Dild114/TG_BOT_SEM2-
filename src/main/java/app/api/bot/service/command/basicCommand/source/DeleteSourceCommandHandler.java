package app.api.bot.service.command.basicCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.ChatStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(9)
@RequiredArgsConstructor
public class DeleteSourceCommandHandler implements BasicCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDDD1 Удалить источник".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD➖ Введите название источника:");
    chatStateService.setState(chatId, "awaiting_source_name_to_delete");
  }
}
