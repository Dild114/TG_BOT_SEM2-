package app.api.service;

import app.api.bot.stubs.exceptions.*;
import app.api.dto.WebsiteDto;
import app.api.entity.*;
import app.api.mapper.WebsiteMapper;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebsiteService {
  private final WebsiteRepository websiteRepository;
  private final WebsiteMapper websiteMapper;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<Website> getUserSources(Long chatId) {
    User user = userRepository.findByIdWithWebsites(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    return user.getWebsites();
  }

  @Transactional
  public void addSourceToUser(Long chatId, String sourceName, String sourceUrl) {
    User user = userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    Website website = Website
        .builder()
        .user(user)
        .sourceActiveStatus(true)
        .sourceName(sourceName)
        .sourceUrl(sourceUrl)
        .build();
    Website websiteSaved = websiteRepository.save(website);
    user.getWebsites().add(websiteSaved);
    userRepository.save(user);
  }

  @Transactional
  public void deleteSourceFromUser(Long chatId, String sourceName) {
    User user = userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    for (Website website : user.getWebsites()) {
      if (website.getSourceName().equals(sourceName)) {
        websiteRepository.delete(website);
        user.getWebsites().remove(website);
        userRepository.save(user);
        return;
      }
    }
    throw new InvalidValueException("Не найден источник с таким названием");
  }

  @Transactional
  public void changeUserSourceStatus(Long chatId, Long sourceId) {
    User user = userRepository.findById(chatId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + chatId));
    Website website = user.getWebsites().stream()
        .filter(w -> w.getSourceId().equals(sourceId))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Website not found: " + sourceId));
    website.setSourceActiveStatus(!website.isSourceActiveStatus());
    websiteRepository.save(website);
  }
}
