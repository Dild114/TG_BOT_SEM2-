package app.channel_parser;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class TelegramChannelParser extends TelegramLongPollingBot {

  private final String channelUsername;

  public TelegramChannelParser(String channelUsername) {
    this.channelUsername = channelUsername;
  }

  @Override
  public String getBotUsername() {
    return "project_channel_parser_bot";
  }

  @Override
  public String getBotToken() {
    return "8073418814:AAFUy3umYAF9VQj6hsCmBwM3lf-1KvJZTrc";
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      if (update.hasChannelPost()) {
        Message message = update.getChannelPost();
        if (channelUsername.equals(message.getChat().getUserName())) {
          log.info("Message received: {}", message.getText());
          processMessage(message);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void processMessage(Message message) {
    System.out.println("\n--- Новое сообщение ---");
    System.out.println("Дата: " + message.getDate());
    System.out.println("Текст: " + message.getText());
    log.info("Получили текст: " + message.getText());
  }

  public static void main(String[] args) {
    try {
      String channelToParse = "moskvik";

      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(new TelegramChannelParser(channelToParse));

      System.out.println("Бот успешно запущен и мониторит канал @" + channelToParse);
      System.out.println("Для остановки нажмите Ctrl+C");

    } catch (TelegramApiException e) {
      System.err.println("Ошибка при запуске бота:");
      e.printStackTrace();
    }
  }
}