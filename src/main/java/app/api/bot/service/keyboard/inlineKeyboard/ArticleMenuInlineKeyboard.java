package app.api.bot.service.keyboard.inlineKeyboard;

import app.api.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleMenuInlineKeyboard {
  private final InlineKeyboardHelper inlineKeyboardHelper;

  //TODO: заменить Article article на нормальную dto и соответственно перепроверить методы
  public InlineKeyboardMarkup createArticleKeyboard(Article article) {
    InlineKeyboardMarkup articleMenuKeyboard = new InlineKeyboardMarkup();

    List<List<InlineKeyboardButton>> articlesKeyboard = new ArrayList<>();

    boolean isWatchingBrief = article.getStatusOfWatchingBriefContent();
    articlesKeyboard.add(
      List.of(
        inlineKeyboardHelper.createCallbackButton(
          !isWatchingBrief ? "🔽 Развернуть" : "🔼 Свернуть",
        "change_article_brief_" + article.getId()
        )
      )
    );

    InlineKeyboardButton urlButton = inlineKeyboardHelper.createUrlButton("🔗", article.getUrl());
    InlineKeyboardButton closeButton = inlineKeyboardHelper.createCallbackButton(!article.getFavoriteStatus() ? "\uD83E\uDD0D" : "❤\uFE0F", "change_article_liked_" + article.getId());

    articlesKeyboard.add(List.of(urlButton, closeButton));

    articleMenuKeyboard.setKeyboard(articlesKeyboard);
    return articleMenuKeyboard;
  }
}
