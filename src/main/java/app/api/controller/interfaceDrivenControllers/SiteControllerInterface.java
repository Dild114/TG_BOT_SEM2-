package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "SiteControllerInterface", description = "Управление сайтами")
@RequestMapping("/site")
public interface SiteControllerInterface {

  @Operation(summary = "Получить все сайты")
  @ApiResponse(responseCode = "200", description = "Получены все сайты")
  @GetMapping("/all")
  ResponseEntity<Map<String, Integer>> getSites();

  @Operation(summary = "Получить сайты пользователя по ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Получены сайты пользователя"),
      @ApiResponse(responseCode = "400", description = "Некорректные данные")
  })
  @GetMapping
  ResponseEntity<List<Site>> mySites(
      @Parameter(description = "ID пользователя", required = true)
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @RequestBody Long userId
  );

  @Operation(summary = "Добавить сайт пользователю")

  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Добавлен сайт пользователю"),
      @ApiResponse(responseCode = "400", description = "Некорректные данные")
  })
  @PatchMapping("/{siteId}")
  ResponseEntity<SiteId> addSite(
      @Parameter(description = "ID сайта", required = true)
      @Valid @Min(value = 1, message = "Site ID must be greater than 0") @PathVariable Long siteId,

      @Parameter(description = "ID пользователя", required = true)
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @RequestBody Long userId
  );

  @Operation(summary = "Удалить сайт пользователя")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Сайт пользователя удален"),
      @ApiResponse(responseCode = "404", description = "Сайт не найден")
  })
  @DeleteMapping("/{siteId}")
  ResponseEntity<Void> deleteSite(
      @Parameter(description = "ID сайта", required = true)
      @Valid @Min(value = 1, message = "Site ID must be greater than 0") @PathVariable Long siteId,

      @Parameter(description = "ID пользователя", required = true)
      @Valid @Min(value = 1, message = "User ID must be greater than 0") @RequestBody Long userId
  );
}
