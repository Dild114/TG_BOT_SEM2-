package app.api.bot.service.command.basicCommand.settings;

import app.api.bot.service.ChatStateService;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(32)
@RequiredArgsConstructor
public class GetBriefArticleOnSettingsCommandHandler implements BasicCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;
  private final UserService userService;

  @Override
  public boolean canHandle(String messageText) {
    return "Получать краткие содержания статей".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteLastBotMessage(chatId);
    chatStateService.clearState(chatId);
    userService.changeUserMakeBriefStatus(chatId, true);
    messageSenderService.sendTextMessage(chatId, "☑\uFE0F Получение кратких содержаний включено");
  }
}
