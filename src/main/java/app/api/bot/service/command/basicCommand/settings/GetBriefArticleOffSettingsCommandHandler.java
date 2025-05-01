package app.api.bot.service.command.basicCommand.settings;

import app.api.bot.service.ChatStateService;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.stubs.user.UserServiceStub;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(33)
@RequiredArgsConstructor
public class GetBriefArticleOffSettingsCommandHandler implements BasicCommandHandler {
  private final ChatStateService chatStateService;
  private final MessageSenderService messageSenderService;
  private final UserServiceStub userServiceStub;

  @Override
  public boolean canHandle(String messageText) {
    return "Не получать краткие содержания".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    chatStateService.clearState(chatId);
    userServiceStub.changeUserMakeBriefStatus(chatId, false);
    messageSenderService.sendTextMessage(chatId, "☑\uFE0F Получение кратких содержаний выключено");
  }
}
