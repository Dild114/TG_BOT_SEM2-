package app.api.bot.service.command.basicCommand;

import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.mainMenu.MainMenuMessageService;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@Order(20)
@RequiredArgsConstructor
public class GoBackCommandHandler implements BasicCommandHandler {
  private final MainMenuMessageService mainMenuMessageService;
  private final UserService userService;

  @Override
  public boolean canHandle(String messageText) {
    return "↩ Главная".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    mainMenuMessageService.sendMainMenuMessage(chatId);
    userService.clearState(chatId);
  }
}
