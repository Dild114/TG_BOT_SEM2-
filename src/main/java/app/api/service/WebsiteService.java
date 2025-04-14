package app.api.service;

import app.api.dto.UserDto;
import app.api.dto.WebsiteDto;
import app.api.entity.*;
import app.api.mapper.UserMapper;
import app.api.mapper.WebsiteMapper;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class WebsiteService {
  private final WebsiteRepository websiteRepository;
  private final WebsiteMapper websiteMapper;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public Set<WebsiteDto> getAllWebsitesByUserId(UserId userId) {
    List<Website> websites = websiteRepository.findAllWebsitesByUserId(userId.getId());
    return websites.stream()
            .map(websiteMapper::toDto)
            .collect(Collectors.toSet());
  }


  public void addWebsiteByUserId(WebsiteDto websiteDto) {
    Long userId = websiteDto.getUserId();
    User user = userRepository.findById(new UserId(userId))
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

    Website website = websiteMapper.toEntity(websiteDto);
    website.setUser(user);
    user.getWebsites().add(website);
    userRepository.save(user);

  }

  public void removeWebsiteByUserId(Long userId, Long websiteId) {
    User user = userRepository.findById(new UserId(userId))
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

    Website website = websiteRepository.findById(new WebsiteId(websiteId))
        .orElseThrow(() -> new EntityNotFoundException("Website not found: " + userId));;

    user.getWebsites().remove(website);
    website.setUser(null);
    userRepository.save(user);

  }
}