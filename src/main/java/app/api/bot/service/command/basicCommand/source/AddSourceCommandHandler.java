package app.api.bot.service.command.basicCommand.source;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.ChatStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(8)
@RequiredArgsConstructor
public class AddSourceCommandHandler implements BasicCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;

  @Override
  public boolean canHandle(String messageText) {
    return "➕ Добавить источник".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    if (chatStateService.getState(chatId) != null) {
      messageSenderService.deleteLastBotMessage(chatId);
    }
    messageSenderService.sendTextMessage(chatId, "\uD83D\uDCDD➕ Введите название источника:");
    chatStateService.setState(chatId, "awaiting_source_name_to_add");
  }
}
