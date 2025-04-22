package app.api.bot.service.keyboard.replyKeyboard.keyboard;


import app.api.bot.service.keyboard.replyKeyboard.replyKeyboardInterface.ReplyKeyboardInterface;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Getter
@Component
public class MainMenuReplyKeyboard implements ReplyKeyboardInterface {
  private final ReplyKeyboardMarkup keyboardMarkup;

  public MainMenuReplyKeyboard() {
    keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setResizeKeyboard(true);
    keyboardMarkup.setOneTimeKeyboard(false);
    keyboardMarkup.setSelective(true);

    KeyboardRow row1 = new KeyboardRow();
    row1.add("\uD83D\uDCF0 Новости");

    KeyboardRow row2 = new KeyboardRow();
    row2.add("\uD83C\uDF10 Источники");

    KeyboardRow row3 = new KeyboardRow();
    row3.add("\uD83D\uDCD1 Категории");

    KeyboardRow row4 = new KeyboardRow();
    row4.add("ℹ️ Инструкция");

    keyboardMarkup.setKeyboard(List.of(row1, row2, row3, row4));
  }
}
