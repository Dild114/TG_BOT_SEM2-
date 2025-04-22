package app.api.bot.service.message.news.articles;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.keyboard.inlineKeyboard.ArticleMenuInlineKeyboard;
import app.api.bot.service.message.MessageTrackingService;
import app.api.bot.stubs.Article;
import app.api.bot.stubs.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleMessageService {
  private final ArticleMenuInlineKeyboard articleMenuInlineKeyboard;
  private final MessageSenderService messageSenderService;
  private final MessageTrackingService messageTrackingService;

  //TODO: заменить на UserService.getCountArticlesInOneIteration(chatId) или что-то в этом роде
  private final User user;

  //TODO: заменить List<Article> на то, что будет нужно.
  public void sendArticles(long chatId, List<Article> articles) {
    for (Article article : articles) {
      SendMessage sendArticle = new SendMessage();
      sendArticle.setText(getMessageForArticleWithoutBrief(article));
      sendArticle.setChatId(chatId);

      sendArticle.setReplyMarkup(articleMenuInlineKeyboard.createArticleKeyboard(article));
      messageSenderService.sendUndeletableMessage(chatId, sendArticle);
    }
  }

  //TODO: тут как-то подумать и изменить articleId, т.к. или посмотреть, как оно выглядит в готовой реализации
  public void updateArticleAndArticleMenu(long chatId, int messageId, int articleId, List<Article> articles) {
    Article article = articles.get(articleId);

    String text = article.getStatusOfWatchingBriefContent()
      ? getMessageForArticleWithBrief(article)
      : getMessageForArticleWithoutBrief(article);

    EditMessageText editMessage = new EditMessageText();
    editMessage.setChatId(chatId);
    editMessage.setMessageId(messageId);
    editMessage.setText(text);

    editMessage.setReplyMarkup(articleMenuInlineKeyboard.createArticleKeyboard(article));

    messageSenderService.updateUndeletableMessage(editMessage);
  }


  //TODO: Возможно изменить/добавить какие-то эмодзи и тп, т.к. в заглушке всё по умолчанию красиво
  private String getMessageForArticleWithoutBrief(Article article) {
    return article.getName() + "\n" + article.getCategoryName() + "\n" + article.getParsedTime();
  }

  private String getMessageForArticleWithBrief(Article article) {
    return article.getName()
      + "\n" + article.getCategoryName()
      + "\n" + article.getParsedTime()
      + "\n\n\uD83D\uDCDA Краткий обзор\n"
      + article.getBriefContent();
  }
}
