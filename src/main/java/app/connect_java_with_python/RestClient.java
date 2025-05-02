package app.connect_java_with_python;

import app.api.entity.Article;
import app.api.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
public class RestClient {
  private static final String BASE_URL = "http://localhost:8000";
  private static final RestTemplate restTemplate = new RestTemplate();

  // возвращается индекс из массива категории,
  // который больше всего подходит или null если такого нет
  public static Integer getCategoryArticle(String textArticle, List<String> categories) {
;    String url = BASE_URL + "/articles";
    Map<String, Object> body = new HashMap<>();
    body.put("article", textArticle);
    body.put("categories", categories);
    Integer response = restTemplate.postForObject(url, body, Integer.class);
    for (String category : categories) {
      log.info("{} перечисление категорий в методе resClient getCategoryArticle", category);
    }
    log.info("getCategoryArticle: {} by url {} list categories {}, textArticle {}", response, url, categories, textArticle);
    if (response == null || response < 0 || response > categories.size()) {
      log.info("response не валидный {}", response);
      return null;
    } else {
      return response;
    }
  }

  // также как прошлый только возвращает -2 если не прошел по цензуре
//  public static Integer getCategoryArticleWithModerate(Article article, List<Category> categories) {
//    String url = BASE_URL + "/articleswithmoderate";
//    Integer response = restTemplate.postForObject(url, , Integer.class);
//    try {
//      log.info("getCategoryArticleWithModerate: {} by url {}", response, url);
//      return response;
//    } catch (Exception e) {
//      throw new NoSuchElementException(e);
//    }
//  }

  public static String getRetelling(String textArticle) {
    String urlRetelling = BASE_URL + "/retelling";
    log.info("python retelling request");
    return restTemplate.postForObject(urlRetelling, urlRetelling, String.class);
  }

  public static Map<String, String> getListArticleByUrl(String url) {
    String urlParsing = BASE_URL + "/parsing";
    log.info("parsing2 url: {}", url);
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("url", url);

    String[] responseParsing = restTemplate.postForObject(
        urlParsing,
        requestBody,
        String[].class
    );
    log.info(responseParsing.toString() + " parsing after request");

    // map<url, descr>
    Map<String, String> articleMap = new HashMap<>();
    for (int i = 1; i < responseParsing.length; i++) {
     articleMap.put(responseParsing[i], responseParsing[i - 1]);
    }
    return articleMap;
  }

  public static String getNameArticle(String textArticle) {
    String urlRetelling = BASE_URL + "/very_short_retelling";
//    Map<String, String>
    log.info("get Name article");
    Map<String, String> request = new HashMap<>();
    request.put("article", textArticle);
    return restTemplate.postForObject(urlRetelling, request, String.class);
  }
}
