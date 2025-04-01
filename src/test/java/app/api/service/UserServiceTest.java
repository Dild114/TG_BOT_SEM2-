package app.api.service;

import app.api.dto.UserDto;
import app.api.entity.User;
import app.api.entity.Website;
import app.api.mapper.UserMapper;
import app.api.repository.UserRepository;
import app.api.repository.WebsiteRepository;
import com.sun.jdi.PrimitiveValue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private WebsiteRepository websiteRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserService userService;

  @Test
  public void testAddWebsiteToUser() {
    User user = new User();
    Website website = new Website();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(websiteRepository.findById(any())).thenReturn(Optional.of(website));
    when(userMapper.toDto(any())).thenReturn(UserDto.builder()
                    .websiteIds(Set.of(1L))
            .build());

    UserDto resultDto = userService.addWebsiteToUser(1L, 1L);
    Set<Long> websiteIds = resultDto.getWebsiteIds();
    Assert.assertEquals(1, websiteIds.size());
  }
}
