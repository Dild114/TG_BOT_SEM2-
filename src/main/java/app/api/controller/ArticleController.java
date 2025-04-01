package app.api.controller;

import app.api.dto.ArticleDto;
import app.api.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

  private final ArticleService articleService;

  @GetMapping("/{id}")
  public ArticleDto getArticle(@PathVariable("id") Long id) {
    return articleService.getArticle(id);
  }

}