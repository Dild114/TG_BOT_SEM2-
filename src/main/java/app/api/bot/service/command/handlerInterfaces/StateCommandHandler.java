package app.api.bot.service.command.handlerInterfaces;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface StateCommandHandler {
  boolean canHandle(long chatId);
  void handle(Message message);
}
