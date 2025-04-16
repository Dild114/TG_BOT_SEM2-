package app.api.bot.service.HandlerInterfaces;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MenuCommandHandler {
  boolean canHandle(String messageText);
  void handle(Message message);
}
