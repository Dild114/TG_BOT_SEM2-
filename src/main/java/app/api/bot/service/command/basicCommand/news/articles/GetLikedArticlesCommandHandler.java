package app.api.bot.service.command.basicCommand.news.articles;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.command.handlerInterfaces.BasicCommandHandler;
import app.api.bot.service.message.news.articles.ArticleMessageService;
import app.api.bot.stubs.article.ArticleServiceStub;
import app.api.bot.stubs.article.ArticleStub;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@Order(32)
@RequiredArgsConstructor
public class GetLikedArticlesCommandHandler implements BasicCommandHandler {
  private final ArticleMessageService articleMessageService;
  private final MessageSenderService messageSenderService;
  //TODO: заменить заглушку на нормальный сервис и думать, где обрабатывать кол-во получаемых статей и порядок их
  private final ArticleServiceStub articleServiceStub;

  @Override
  public boolean canHandle(String messageText) {
    return "❤\uFE0F Избранное".equals(messageText);
  }

  @Override
  public void handle(Message message) {
    long chatId = message.getChatId();
    List<ArticleStub> articles = articleServiceStub.getLikedUserArticles(chatId);
    messageSenderService.deleteAllMessagesAfterReplyKeyboard(chatId);
    if (!articles.isEmpty()) {
      articleMessageService.sendArticles(chatId, articles);
    } else {
      messageSenderService.sendTextMessage(chatId, "\uD83E\uDD14 Ни одна статья не была добавлена в избранное");
    }
  }
}
