package app.api.mapper;

import app.api.dto.ArticleDto;
import app.api.dto.WebsiteDto;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Website;
import app.api.entity.WebsiteId;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WebsiteMapper {

  public  WebsiteDto toWebsiteDto(Website website) {
    return null;
  }
}
