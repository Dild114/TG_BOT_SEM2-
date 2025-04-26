package app.api.bot.service.keyboard.inlineKeyboard;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class CloseMessageInlineKeyboard {
  private final InlineKeyboardHelper inlineKeyboardHelper;
  @Getter
  private final InlineKeyboardMarkup closeButton;

  public CloseMessageInlineKeyboard(InlineKeyboardHelper inlineKeyboardHelper) {
    this.inlineKeyboardHelper = inlineKeyboardHelper;
    this.closeButton = createCloseMessageKeyboard();
  }

  private InlineKeyboardMarkup createCloseMessageKeyboard() {
    InlineKeyboardMarkup closeMessageKeyboard = new InlineKeyboardMarkup();

    InlineKeyboardButton closeMessageButton = inlineKeyboardHelper.createCallbackButton("❎ Закрыть", "close_message");

    List<InlineKeyboardButton> row1 = List.of(closeMessageButton);

     closeMessageKeyboard.setKeyboard(List.of(row1));

     return closeMessageKeyboard;
  }
}
