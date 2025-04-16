package app.api.bot.service.menuCommand;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.MenuCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(2)
@RequiredArgsConstructor
public class HelpCommandHandler implements MenuCommandHandler {
  private final BotMessageService botMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "/help".equals(messageText) || "ℹ️ Инструкция".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    botMessageService.sendHelpMessage(chatId);
  }
}
