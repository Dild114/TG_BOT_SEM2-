package app.api.controller;

public enum Sites {
  SITE1("https://xakep.ru/"),
  SITE2("https://habr.com/ru/news/"),
  SITE3("https://timeweb.com/ru/community/"),
  SITE4("https://3dnews.ru/"),
  SITE5("https://www.ixbt.com/news/");

  private final String url;

  Sites(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}