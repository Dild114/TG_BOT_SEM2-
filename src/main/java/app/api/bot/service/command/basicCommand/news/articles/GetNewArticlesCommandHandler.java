package app.api.bot.service.command.basicCommand.news.articles;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.news.articles.ArticleMessageService;
import app.api.entity.*;
import app.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@Order(15)
@RequiredArgsConstructor
public class GetNewArticlesCommandHandler implements BasicCommandHandler {
  private final ArticleMessageService articleMessageService;
  private final MessageSenderService messageSenderService;
  private final ArticleService articleService;
  private final UserService userService;

  @Override
  public boolean canHandle(String messageText) {
    return "\uD83D\uDCD6 Новые статьи".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    long countResponseArticlesForUser = userService.getUserCountArticlesInOneRequest(chatId);
    List<Article> articles = articleService.getNewUserArticles(chatId, countResponseArticlesForUser);
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    if (!articles.isEmpty()) {
      articleMessageService.sendArticles(chatId, articles);
    } else {
      messageSenderService.sendTextMessage(chatId, "\uD83D\uDE4C Новых статей пока нет");
    }
  }
}