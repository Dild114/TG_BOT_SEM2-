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
  public void saveArticle(ArticleDto articleDto) { //сохранение новой статьи в бд
    articleRepository.save(articleMapper.toEntity(articleDto));
    log.info("Article (name = {}) was saved", articleDto.getName());
  }

  @Transactional(readOnly = true)
  public ArticleDto getArticle(Long id) { //получает статью по ее id
    Article article = articleRepository.findById(new ArticleId(id))
            .orElseThrow(() -> new EntityNotFoundException("Article not found"));
    log.info("Article (id = {}) was found", id);
    return articleMapper.toDto(article);
  }

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticles() { //получает список всех статей
   return articleRepository.findAll().stream()
           .map(articleMapper::toDto)
           .toList();
  }

  @Transactional
  public void deleteArticle(Long id) { //удаление статьи по ее id
    articleRepository.deleteById(new ArticleId(id));
    log.info("Article (id = {}) was deleted", id);
  }

  @Transactional
  public void updateArticle(ArticleDto articleDto) { //обновление информации о статье
    articleRepository.save(articleMapper.toEntity(articleDto));
  }

  @Transactional(readOnly = true)
  public List<ArticleDto> getArticlesByCategory(String category) { //получение всех статей по соответсвующей категории
    return articleRepository.findAllByCategoryName(category).stream()
            .map(articleMapper::toDto)
            .toList();
  }
}
