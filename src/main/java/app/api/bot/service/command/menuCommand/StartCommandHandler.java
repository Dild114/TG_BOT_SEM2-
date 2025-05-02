package app.api.bot.service.command.menuCommand;

import app.api.bot.service.command.handlerInterfaces.MenuCommandHandler;
import app.api.bot.service.message.welcome.WelcomeMessageService;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(1)
@RequiredArgsConstructor
public class StartCommandHandler implements MenuCommandHandler {
  private final UserService userService;
  private final WelcomeMessageService welcomeMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "/start".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    String userName = message.getFrom().getFirstName();
    userService.clearState(chatId);

    userService.deleteUser(chatId);
    userService.createUser(chatId);

    welcomeMessageService.sendWelcomeMessage(chatId, userName);
  }
}
