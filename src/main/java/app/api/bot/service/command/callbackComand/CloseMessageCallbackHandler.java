package app.api.bot.service.command.callbackComand;

import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(2)
@RequiredArgsConstructor
public class CloseMessageCallbackHandler implements CallbackCommandHandler {
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String callbackData) {
    return "close_message".equals(callbackData);
  }

  @Override
  public void handle(CallbackQuery callbackQuery) {
    long chatId = callbackQuery.getMessage().getChatId();
    int messageId = callbackQuery.getMessage().getMessageId();
    messageSenderService.deleteMessage(chatId, messageId);
  }
}
