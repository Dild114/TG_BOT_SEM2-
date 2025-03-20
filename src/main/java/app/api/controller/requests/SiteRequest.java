package app.api.controller.requests;

import jakarta.validation.constraints.NotNull;

public class SiteRequest {
  @NotNull(message = "URL cannot be null")
  public String url;
}
