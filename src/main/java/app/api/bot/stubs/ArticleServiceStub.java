package app.api.bot.stubs;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class ArticleServiceStub {
  private final List<Article> articles = new ArrayList<>();

  public ArticleServiceStub() {
    for (int i = 0; i < 5; i++) {
      articles.add(new Article(i));
    }
  }

  public void changeStatusBrief(int articleId) {
    articles.get(articleId).setStatusOfWatchingBriefContent(!articles.get(articleId).getStatusOfWatchingBriefContent());
  }
}
