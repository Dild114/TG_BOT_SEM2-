package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Article;
import app.api.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "ArticleControllerInterface", description = "Управление статьями")
public interface ArticleControllerInterface {

  @Operation(summary = "Получить статьи пользователя")
  @ApiResponse(responseCode = "200", description = "Получены статьи пользователя")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @GetMapping("/articles")
  ResponseEntity<Map<Article, Category>> getArticles(
      @Parameter(description = "ID пользователя", required = true)
      @RequestBody int id
  );
}
