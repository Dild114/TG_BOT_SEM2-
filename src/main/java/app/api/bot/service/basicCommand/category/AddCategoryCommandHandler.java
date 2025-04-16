package app.api.bot.service.basicCommand.category;

import app.api.bot.service.BotMessageService;
import app.api.bot.service.HandlerInterfaces.BasicCommandHandler;
import app.api.bot.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(6)
@RequiredArgsConstructor
public class AddCategoryCommandHandler implements BasicCommandHandler {
  private final UserStateService userStateService;
  private final BotMessageService botMessageService;

  @Override
  public boolean canHandle(String messageText) {
    return "➕ Добавить категорию".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    if (userStateService.getState(chatId) != null) {
      botMessageService.deleteLastBotMessage(chatId);
    }
    botMessageService.sendMessage(chatId, "\uD83D\uDCDD➕ Введите название категории:");
    userStateService.setState(chatId, "awaiting_category_name_to_add");
  }
}
