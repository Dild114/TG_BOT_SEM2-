package app.api.service;

import app.api.dto.WebsiteDto;
import app.api.entity.*;
import app.api.mapper.WebsiteMapper;
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

  @Transactional(readOnly = true)
  public Set<WebsiteDto> getAllWebsitesByUserId(UserId userId) {
    List<Website> websites = websiteRepository.findAllWebsiteByUser(userId);
    return websites.stream()
            .map(websiteMapper::toDto)
            .collect(Collectors.toSet());
  }

  @Transactional
  public void addWebsite(WebsiteDto websiteDto) {
    websiteRepository.save(websiteMapper.toEntity(websiteDto));
  }

  @Transactional
  public void deleteWebsite(WebsiteDto websiteDto) {
    WebsiteId websiteId = new WebsiteId(websiteDto.getId());
    if(!websiteRepository.existsById(websiteId)) {
      throw new EntityNotFoundException("Website not found");
    } else{
      websiteRepository.deleteById(websiteId);
    }
  }
}