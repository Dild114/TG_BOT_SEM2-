package app.api.service;

import app.api.dto.ArticleDto;
import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.mapper.ArticleMapper;
import app.api.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;

  @Transactional
  public void saveArticle(ArticleDto articleDto) {
    articleRepository.save(articleMapper.toEntity(articleDto));
  }

  @Transactional(readOnly = true)
  public ArticleDto getArticle(Long id) {
    Article article = articleRepository.findById(new ArticleId(id))
            .orElseThrow(() -> new EntityNotFoundException("Article not found"));
    return articleMapper.toDto(article);
  }

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticles() {
   return articleRepository.findAll().stream()
           .map(articleMapper::toDto)
           .toList();
  }

  @Transactional
  public void deleteArticle(Long id) {
    articleRepository.deleteById(new ArticleId(id));
  }

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticlesByCategory(String category) {
    return articleRepository.findAllByCategoryName(category).stream()
            .map(articleMapper::toDto)
            .toList();
  }
}