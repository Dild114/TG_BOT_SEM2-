package app.api.controller.interfaceDrivenControllers;

import app.api.controller.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserControllerInterface", description = "Управление пользователями")
@RequestMapping("/signup")
public interface UserControllerInterface {

  @Operation(summary = "Создать пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь создан")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @ApiResponse(responseCode = "500", description = "Ошибка сервера")
  @PostMapping
  ResponseEntity<?> createUser(
      @Parameter(description = "Имя пользователя", required = true)
      @RequestBody UserRequest userRequest
  );

  @Operation(summary = "Удалить пользователя по id")
  @ApiResponse(responseCode = "204", description = "Пользователь удален")
  @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteUser(
      @Parameter(description = "ID пользователя", example = "123")
      @PathVariable int id
  );

  @Operation(summary = "Обновить данные пользователя по id")
  @ApiResponse(responseCode = "200", description = "Данные пользователя обновлены")
  @ApiResponse(responseCode = "404", description = "Пользователь не найден")
  @PutMapping("/{id}")
  ResponseEntity<?> updateUserData(
      @Parameter(description = "ID пользователя", example = "123")
      @PathVariable int id,

      @Parameter(description = "Пользователь", required = true)
      @RequestBody UserRequest userRequest
  );

  @Operation(summary = "Обновить имя пользователя")
  @ApiResponse(responseCode = "200", description = "Имя пользователя обновлено")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @PatchMapping("/{id}")
  ResponseEntity<?> updateUserName(
      @Parameter(description = "ID пользователя", example = "123")
      @PathVariable int id,

      @Parameter(description = "Новое имя пользователя", required = true)
      @RequestBody String name
  );
}