package app.api.bot.service.basicCommand.settings;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.BasicCommandHandler;
import app.api.bot.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(4)
@RequiredArgsConstructor
public class ColumnSettingCommandHandler implements BasicCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "Изменить кол-во элементов, отображаемых на одной странице".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    if (userStateService.getState(chatId) != null) {
      botMessageService.deleteLastBotMessage(chatId);
    }
    botMessageService.sendMessage(chatId, "\uD83D\uDD22 Введите новое значение:");
    userStateService.setState(chatId, "awaiting_new_column_height");
  }
}
