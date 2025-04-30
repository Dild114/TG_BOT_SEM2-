package app.api.bot.service.message.help;

import app.api.bot.service.keyboard.inlineKeyboard.CloseMessageInlineKeyboard;
import app.api.bot.service.MessageSenderService;
import app.api.bot.service.message.MessageTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class HelpMessageService {
  private final MessageTemplateService messageTemplateService;
  private final CloseMessageInlineKeyboard closeMessageInlineKeyboard;
  private final MessageSenderService messageSenderService;

  public void sendHelpMessage(long chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(messageTemplateService.getHelpText());
    sendMessage.setReplyMarkup(closeMessageInlineKeyboard.getCloseButton());

    messageSenderService.sendUndeletableMessage(chatId, sendMessage);
  }
}
