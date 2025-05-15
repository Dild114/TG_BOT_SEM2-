package app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

  private Long id;

  private String name;

  private String url;

  private OffsetDateTime creationDate;

  private String category;

  private String website;

  private String userId;

  private boolean statusOfWatchingBriefContent;

  private boolean favoriteStatus;

  private String brief = null;
}
