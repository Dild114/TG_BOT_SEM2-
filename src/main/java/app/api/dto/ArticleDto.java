package app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

  private Long id;

  private String name;

  private String url;

  private String creationDate;

  private Set<Long> categoryIds;

  private Set<Long> userIds;
}
