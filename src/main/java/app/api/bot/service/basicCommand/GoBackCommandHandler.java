package app.api.bot.service.basicCommand;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.BasicCommandHandler;
import app.api.bot.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(20)
@RequiredArgsConstructor
public class GoBackCommandHandler implements BasicCommandHandler {
  private final BotMessageService botMessageService;
  private final UserStateService userStateService;

  @Override
  public boolean canHandle(String messageText) {
    return "↩️ Главная".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    botMessageService.sendMainMenuMessage(chatId);
    userStateService.clearState(chatId);
  }
}
