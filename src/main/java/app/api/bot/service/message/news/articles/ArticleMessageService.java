package app.api.bot.service.message.news.articles;

import app.api.bot.service.MessageSenderService;
import app.api.bot.service.keyboard.inlineKeyboard.ArticleMenuInlineKeyboard;
import app.api.bot.stubs.article.ArticleServiceStub;
import app.api.bot.stubs.article.ArticleStub;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleMessageService {
  private final ArticleMenuInlineKeyboard articleMenuInlineKeyboard;
  private final ArticleServiceStub articleServiceStub;
  private final MessageSenderService messageSenderService;

  //TODO: заменить List<Article> на то, что будет нужно. Добавить удаление всех категорий из репо;
  public void sendArticles(long chatId, List<ArticleStub> articles) {
    for (ArticleStub article : articles) {
      SendMessage sendArticle = new SendMessage();
      sendArticle.setText(getMessageForArticleWithoutBrief(article));
      sendArticle.setChatId(chatId);

      sendArticle.setReplyMarkup(articleMenuInlineKeyboard.createArticleKeyboard(article));
      messageSenderService.sendMessage(chatId, sendArticle);
    }
  }

  //TODO: тут как-то подумать и изменить articleId, т.к. или посмотреть, как оно выглядит в готовой реализации
  public void updateArticleAndArticleMenu(long chatId, int messageId, int articleId) {
    ArticleStub article = articleServiceStub.getUserArticle(chatId, articleId);

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
  private String getMessageForArticleWithoutBrief(ArticleStub article) {
    return "\uD83D\uDCCB " + article.getArticleName() + "\n Категория: \"" + article.getArticleCategoryName() + "\" \n\uD83D\uDCC5 "
      + article.getArticleParsedDate() + " | \uD83D\uDD53 " + article.getArticleParsedTime();
  }

  private String getMessageForArticleWithBrief(ArticleStub article) {
    String brief = article.getBriefContent();
    if (!brief.isEmpty()) {
      brief = "\uD83D\uDCAC Краткое содержание: \n" + brief;
    } else {
      brief = "Краткое содержание отсутствует \uD83E\uDD17";
    }
    return "\uD83D\uDCCB " + article.getArticleName() + "\n Категория: \"" + article.getArticleCategoryName() + "\" \n\uD83D\uDCC5 "
      + article.getArticleParsedDate() + " | \uD83D\uDD53 " + article.getArticleParsedTime() + "\n\n" + brief;
  }
}
