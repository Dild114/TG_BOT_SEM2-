package app.api.bot.service.command.basicCommand.settings;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(33)
@RequiredArgsConstructor
public class GetBriefArticleOffSettingsCommandHandler implements BasicCommandHandler {
  private final MessageSenderService messageSenderService;
  private final UserService userService;

  @Override
  public boolean canHandle(String messageText) {
    return "Не получать краткие содержания".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    userService.clearState(chatId);
    userService.changeUserMakeBriefStatus(chatId, false);
    messageSenderService.sendTextMessage(chatId, "☑\uFE0F Получение кратких содержаний выключено");
  }
}
