package app.api.bot.service.command.callbackComand.article;

import app.api.bot.service.command.handlerInterfaces.CallbackCommandHandler;
import app.api.bot.service.message.news.articles.ArticleMessageService;
import app.api.bot.stubs.ArticleServiceStub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Order(10)
@Slf4j
@RequiredArgsConstructor
public class StatusBriefArticleCallbackHandler implements CallbackCommandHandler {
  //TODO: заменить заглушку на что-то нормальное
  private final ArticleServiceStub articleServiceStub;
  private final ArticleMessageService articleMessageService;

  @Override
  public boolean canHandle(String callbackData) {
    return callbackData.startsWith("article_change_");
  }

  //TODO: тут тоже не забыть проверить, как мы в итоге храним, по articleId, или как ещё
  @Override
  public void handle(CallbackQuery callbackQuery) {
    String callbackData = callbackQuery.getData();
    long chatId = callbackQuery.getMessage().getChatId();
    int messageId = callbackQuery.getMessage().getMessageId();

    String[] parts = callbackData.split("_");
    int articleId = Integer.parseInt(parts[3]);

    articleServiceStub.changeStatusBrief(articleId);
    articleMessageService.updateArticleAndArticleMenu(chatId, messageId, articleId, articleServiceStub.getArticles());
  }
}
