package app.api.bot.service.command.basicCommand.category;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.ChatStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(6)
@RequiredArgsConstructor
public class DeleteCategoryCommandHandler implements BasicCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDDD1 Удалить категорию".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD➖ Введите название категории:");
    chatStateService.setState(chatId, "awaiting_category_name_to_delete");
  }
}
