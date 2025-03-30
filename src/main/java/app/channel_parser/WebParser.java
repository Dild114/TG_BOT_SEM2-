package app.channel_parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WebParser {
  private String url = "https://t.me/s/";

  public WebParser(String channelName) {
    this.url += channelName.substring(1);
  }

  public List<String> getMessage() throws IOException {
    Document doc = Jsoup.connect(url).get();
    Elements messages = doc.select(".tgme_widget_message_text");
    return messages.stream().map(Element::text).collect(Collectors.toList());
  }
}

