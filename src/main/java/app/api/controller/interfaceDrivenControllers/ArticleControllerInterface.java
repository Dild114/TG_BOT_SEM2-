package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Validated
@Tag(name = "ArticleControllerInterface", description = "Управление статьями")
public interface ArticleControllerInterface {

  @Operation(summary = "Получить все статьи")
  @ApiResponse(responseCode = "200", description = "Получены все статьи")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @GetMapping("/articles")
  ResponseEntity<List<Article>> getArticles();

  @Operation(summary = "Получить статьи пользователя")
  @ApiResponse(responseCode = "200", description = "Получены статьи пользователя")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @GetMapping("/articles/{userId}")
  ResponseEntity<Map<Article, Category>> getArticleWithCategory(
      @Parameter(description = "ID пользователя", required = true)
      @Valid @Min(value = 1, message = "Category ID must be greater than 0") @PathVariable Long userId
  );
}
