//package app.api.service;
//
//import app.api.dto.ArticleDto;
//import app.api.entity.Article;
//import app.api.entity.ArticleId;
//import app.api.mapper.ArticleMapper;
//import app.api.repository.ArticleRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ArticleService {
//  private final ArticleRepository articleRepository;
//  private final ArticleMapper articleMapper;
//
//  // вот тут так раз надо учитывать время хранения и удалять статьи которые хранятся дольше чем нужно
//  // те нужно сделать @Query select ... where now - createDateArticle < user.massage_storage_time_day
//  // а дальше лучше сделать @Query delete ... where now - createDateArticle > user.massage_storage_time_day
//  // тут уже находятся статьи которые подходят по времени и удаляются остальные
//
//  @Transactional(readOnly = true)
//  public Set<ArticleDto> getArticlesByUserId(Long userId) {
//   Set<Article> articles = articleRepository.findActiveArticlesByUserId(userId);
//   Set<ArticleDto> articleDtoSet = articles.stream().map(articleMapper::toDto).collect(Collectors.toSet());
//   articleRepository.deleteExpiredArticlesByUserId(userId);
//   return articleDtoSet;
//  }
//
//  @Transactional
//  public void deleteArticle(Long id) {
//    if (!articleRepository.existsById(new ArticleId(id))) {
//      throw new EntityNotFoundException("Article not found with id: " + id);
//    }
//    log.info("Deleting article with id: {}", id);
//    articleRepository.deleteById(new ArticleId(id));
//  }
//
//
//  @Transactional
//  public ArticleDto createArticle(ArticleDto articleDto) {
//    Article article = articleMapper.toEntity(articleDto);
//    articleRepository.save(article);
//    return articleMapper.toDto(article);
//  }
//}