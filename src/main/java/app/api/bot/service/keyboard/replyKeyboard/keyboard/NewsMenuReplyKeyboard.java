package app.api.bot.service.keyboard.replyKeyboard.keyboard;

import app.api.bot.service.keyboard.replyKeyboard.replyKeyboardInterface.ReplyKeyboardInterface;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Getter
@Component
public class NewsMenuReplyKeyboard implements ReplyKeyboardInterface {
  private final ReplyKeyboardMarkup keyboardMarkup;

  public NewsMenuReplyKeyboard() {
    keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("\uD83D\uDCD6 Новые статьи");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("❤\uFE0F Избранное");

    KeyboardRow row3 = new KeyboardRow();
    row3.add("↩ Главная");

    keyboardMarkup.setKeyboard(List.of(row1, row2, row3));
  }
}
