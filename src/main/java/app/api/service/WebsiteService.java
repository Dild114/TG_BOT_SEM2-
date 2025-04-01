package app.api.service;

import app.api.dto.CategoryDto;
import app.api.dto.WebsiteDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.mapper.CategoryMapper;
import app.api.mapper.WebsiteMapper;
import app.api.repository.CategoryRepository;
import app.api.repository.WebsiteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service

public class WebsiteService {
  private final WebsiteRepository websiteRepository;
  private final CategoryRepository categoryRepository;
  private final WebsiteMapper websiteMapper;

  public void getWebSiteByUserId(Long userId) {
   // websiteRepository.findByUsers_id(new UserId(userId))
     //       .stream();
  }
}
