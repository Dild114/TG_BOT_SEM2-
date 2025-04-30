package app.api.bot.service.keyboard.inlineKeyboard;

import app.api.bot.stubs.source.SourceStub;
import app.api.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SourceMenuInlineKeyboard {
  private final InlineKeyboardHelper inlineKeyboardHelper;

  //TODO: заменить sources чтобы нормально соотносилось, (проверить, подходит ли LinkedHashMap)
  public InlineKeyboardMarkup createSourcesList(
    List<Website> sources,
    int pageNum,
    String viewMode,
    int pageSize
  ) {

    InlineKeyboardMarkup sourceListKeyboard = new InlineKeyboardMarkup();

    int countPages = (int) Math.ceil(sources.size() / (double) pageSize);

    int start = (pageNum - 1) * pageSize;
    int end = Math.min((pageNum) * pageSize, sources.size());

    //TODO: аналогично проверить, всё ли норм из-зи LinkedHashMap
    List<Website> currentPageSources = sources.subList(start, end);

    List<List<InlineKeyboardButton>> sourcesKeyboard = new ArrayList<>();

    InlineKeyboardButton viewToggle = inlineKeyboardHelper.createCallbackButton(
      "state".equals(viewMode) ? "Вид: Состояние" : "Вид: Ссылочный",
      "state".equals(viewMode) ? "change_source_view_link_" + pageNum : "change_source_view_state_" + pageNum
    );
    sourcesKeyboard.add(List.of(viewToggle));

    for (Website source : currentPageSources) {
      InlineKeyboardButton btn = "state".equals(viewMode)
        ? inlineKeyboardHelper.createCallbackButton((source.isSourceActiveStatus() ? "✅ " : "❌ ") + source.getSourceName(), "change_source_status_" + source.getSourceId() + "_" + pageNum)
        : inlineKeyboardHelper.createUrlButton("🔗 " + source.getSourceName(), source.getSourceUrl());
      sourcesKeyboard.add(List.of(btn));
    }

    List<InlineKeyboardButton> navigation = inlineKeyboardHelper.buildNavigationButtons("page_source", pageNum, countPages);
    sourcesKeyboard.add(navigation);

    sourceListKeyboard.setKeyboard(sourcesKeyboard);
    return sourceListKeyboard;
  }
}
