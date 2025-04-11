package app.api.integration.service;

import app.api.dto.UserDto;
import app.api.integration.IntegrationTestDataBase;
import app.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import java.util.HashSet;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceIT extends IntegrationTestDataBase {
  private final UserService userService;

  @Test
  void testCreteUser() {
    UserDto dto = UserDto.builder()
            .name("test")
            .telegramId("123456")
            .websiteIds(new HashSet<>())
            .articlesIds(new HashSet<>())
            .categoriesIds(new HashSet<>())
            .build();
    UserDto createdUser = userService.createUser(dto);
    Assertions.assertNotNull(createdUser.getUserId());
  }
}