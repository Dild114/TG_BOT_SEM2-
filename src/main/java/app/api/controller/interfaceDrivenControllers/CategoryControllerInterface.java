package app.api.controller.interfaceDrivenControllers;

import app.api.controller.CategoryRequest;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "CategoryControllerInterface", description = "Управление категориями")
@RequestMapping("/category")
public interface CategoryControllerInterface {

  @Operation(
      summary = "Получить категории пользователя",
      description = "Получает ID пользователя и возвращает список его категорий"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Получены категории пользователя"
//          T
//          content = @Content(
//              mediaType = "application/json",
//              examples = @ExampleObject(
//              value = "{\"userId\" : 1}"
//          ))
      ),
      @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
      @ApiResponse(responseCode = "400", description = "Неккоректное ID пользователя")
  })
  @GetMapping("/user/{userId}")
  ResponseEntity<List<Category>> getMyCategories(
      @Parameter(description = "ID пользователя", required = true, example = "1")
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @PathVariable Long userId
  );

  @Operation(
      summary = "Получить категорию по ID",
      description = "Получает ID категории и возвращает её"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Категория получена"),
      @ApiResponse(responseCode = "404", description = "Категория с таким ID не найдена"),
      @ApiResponse(responseCode = "400", description = "Некорректное ID категории")
  })
  @GetMapping("/{categoryId}")
  ResponseEntity<Category> getCategoryById(
      @Parameter(description = "ID категории", example = "1")
      @Valid @Min(value = 1, message = "Category ID must be greater than 0") @PathVariable Long categoryId
  );

  @Operation(
      summary = "Создать новую категорию пользователю",
      description = "Получает ID пользователя и данные категории, создаёт пользователю новую категорию"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Категория успешно создана"),
      @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
      @ApiResponse(responseCode = "400", description = "Некорректное ID пользователя")
  })
  @PostMapping("/create/{userId}")
  ResponseEntity<CategoryId> createCategoryForUser(
      @Parameter(description = "Название создаваемой категории", example = "IT")
      @Valid @RequestBody CategoryRequest categoryRequest,

      @Valid @Min(value = 1, message = "User ID must be greater than 0") @Parameter(description = "ID пользователя", example = "1")
      @PathVariable Long userId
  );

  @Operation(
      summary = "Удалить все категории пользователя по его ID",
      description = "Получает ID пользователя и удаляет все его категории"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Категории пользователя успешно удалены"),
      @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден"),
      @ApiResponse(responseCode = "400", description = "Некорректное ID пользователя")
  })
  @DeleteMapping("/delete/user/{userId}")
  ResponseEntity<Void> deleteUserCategories(
      @Parameter(description = "ID пользователя", example = "1")
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @PathVariable Long userId
  );

  @Operation(
      summary = "Удалить категорию по ID",
      description = "Получает ID категории и удаляет её"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Категория пользователя удалена"),
      @ApiResponse(responseCode = "404", description = "Категория не найдена"),
      @ApiResponse(responseCode = "400", description = "Некорректное ID категории")
  })
  @DeleteMapping("/delete/{categoryId}")
  ResponseEntity<Void> deleteCategory(
      @Parameter(description = "ID категории", required = true, example = "1")
      @Valid @Min(value = 1, message = "Category ID must be greater than 0") @PathVariable Long categoryId
  );
}
