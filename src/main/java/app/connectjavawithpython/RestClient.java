package app.connectjavawithpython;

import app.api.entity.Article;
import app.api.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class RestClient {
  private static final String BASE_URL = "http://localhost:8080";
  private static final RestTemplate restTemplate = new RestTemplate();

  // возвращается индекс из массива категории,
  // который больше всего подходит или -1 если такого нет
  public static Integer getCategoryArticle(Article article, List<Category> categories) {
    String url = BASE_URL + "/articles";
    Integer response = restTemplate.postForObject(url, new ArticleRequest(article, categories), Integer.class);
    log.info("getCategoryArticle: {} by url {}", response, url);
    try {
      return response;
    } catch (Exception e) {
      throw new NoSuchElementException(e);
    }
  }

  // также как прошлый только возвращает -2 если не прошел по цензуре
  public static Integer getCategoryArticleWithModerate(Article article, List<Category> categories) {
    String url = BASE_URL + "/articleswithmoderate";
    Integer response = restTemplate.postForObject(url, new ArticleRequest(article, categories), Integer.class);
    try {
      log.info("getCategoryArticleWithModerate: {} by url {}", response, url);
      return response;
    } catch (Exception e) {
      throw new NoSuchElementException(e);
    }
  }

  public static String getRetelling(Article article) {
    String urlParsing = BASE_URL + "/parsing";
    String urlRetelling = BASE_URL + "/retelling";
    String responseParsing = restTemplate.postForObject(urlParsing, article.url(), String.class);
    log.info("retelling: {}, {}", urlParsing, urlRetelling);
    return restTemplate.postForObject(urlRetelling, responseParsing, String.class);
  }
}
