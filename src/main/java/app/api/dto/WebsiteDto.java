package app.api.dto;

import app.api.entity.Website;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WebsiteDto {
  private Long id;

  private String url;

  private Long userId;
}
