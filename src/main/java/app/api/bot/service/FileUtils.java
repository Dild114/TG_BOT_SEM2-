package app.api.bot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {
  public static String readFileAsString(String filePath) {
    try {
      return Files.readString(Path.of(filePath));
    } catch (IOException e) {
      throw new RuntimeException("Ошибка при чтении файла: " + filePath, e);
    }
  }
}
