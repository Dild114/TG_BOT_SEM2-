package app.api.bot.service.menuCommand;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.MenuCommandHandler;
import app.api.bot.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(3)
@RequiredArgsConstructor
public class SettingCommandHandler implements MenuCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "/settings".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    userStateService.clearState(chatId);
    botMessageService.sendSettingsMessage(chatId);
  }
}
