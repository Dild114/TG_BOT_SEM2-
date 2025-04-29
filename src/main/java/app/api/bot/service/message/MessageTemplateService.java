package app.api.bot.service.message;

import app.api.bot.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageTemplateService {
  public String getWelcomeText(String userName) {
    String filePath = "src/main/java/app/api/bot/message/welcome.txt";
    String welcomeText = FileUtils.readFileAsString(filePath).replace("{name}", userName);
    return welcomeText;
  }

  public String getHelpText() {
    String filePath = "src/main/java/app/api/bot/message/help.txt";
    String helpText = FileUtils.readFileAsString(filePath);
    return helpText;
  }
}
