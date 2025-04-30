package app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
  private Long categoryId;

  private String categoryName;

  private Long userId;

  private boolean categoryActiveStatus = true;
}