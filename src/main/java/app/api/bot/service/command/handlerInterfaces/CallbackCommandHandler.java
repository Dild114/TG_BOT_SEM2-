package app.api.bot.service.command.handlerInterfaces;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommandHandler {
  boolean canHandle(String callbackData);
  void handle(CallbackQuery query);
}
