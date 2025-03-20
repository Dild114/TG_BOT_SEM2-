package app.api.controller.interfaceDrivenControllers;

import app.api.controller.requests.UserRequest;
import app.api.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Tag(name = "UserControllerInterface", description = "Управление пользователями")
@RequestMapping("/signup")
public interface UserControllerInterface {

  @Operation(summary = "Создать пользователя")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Пользователь создан"),
    @ApiResponse(responseCode = "400", description = "Некорректные данные"),
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  })
  @PostMapping
  ResponseEntity<?> createUser(
      @Parameter(description = "Имя пользователя", required = true)
      @Valid @RequestBody UserRequest userRequest
  );

  @Operation(summary = "Найти пользователя по ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Пользователь найден"),
    @ApiResponse(responseCode = "400", description = "Некорректные данные"),
    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  })
  @GetMapping("/user/{userId}")
  ResponseEntity<User> findUserById(
    @Parameter(description = "ID пользователя", required = true)
    @Valid @PathVariable Long userId
  );

  @Operation(summary = "Удалить пользователя по id")
  @ApiResponse(responseCode = "204", description = "Пользователь удален")
  @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteUser(
      @Parameter(description = "ID пользователя", example = "123")
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @PathVariable Long id
  );

  @Operation(summary = "Обновить данные пользователя по id")
  @ApiResponse(responseCode = "200", description = "Данные пользователя обновлены")
  @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  @PutMapping("/{id}")
  ResponseEntity<?> updateUserData(
      @Parameter(description = "ID пользователя", example = "123")
      @Valid @Min(value = 1, message = "Category ID must be greater than 0") @PathVariable Long id,

      @Parameter(description = "Пользователь", required = true)
      @Valid @RequestBody UserRequest userRequest
  );
}