package app.api.bot.service.command.menuCommand;

import app.api.bot.service.command.handlerInterfaces.MenuCommandHandler;
import app.api.bot.service.ChatStateService;
import app.api.bot.service.message.settings.SettingsMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(3)
@RequiredArgsConstructor
public class SettingCommandHandler implements MenuCommandHandler {
  private final ChatStateService chatStateService;
  private final SettingsMessageService settingsMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "/settings".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    chatStateService.clearState(chatId);
    settingsMessageService.sendSettingsMessage(chatId);
  }
}
