package app.api.bot.service.command.basicCommand.news;

import app.api.bot.service.ChatStateService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.news.NewsMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(14)
@RequiredArgsConstructor
public class GetNewsCommandHandler implements BasicCommandHandler {
  private final NewsMessageService newsMessageService;
  private final ChatStateService chatStateService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDCF0 Новости".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    chatStateService.setState(chatId, "getting_articles");
    newsMessageService.sendNewsMenuMessage(chatId);
  }
}
