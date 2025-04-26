package app.api.bot.service.command.menuCommand;

import app.api.bot.service.command.handlerInterfaces.MenuCommandHandler;
import app.api.bot.service.message.help.HelpMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(2)
@RequiredArgsConstructor
public class HelpCommandHandler implements MenuCommandHandler {
  private final HelpMessageService helpMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "/help".equals(messageText) || "ℹ️ Инструкция".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    helpMessageService.sendHelpMessage(chatId);
  }
}
