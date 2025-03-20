package app.api.controller.requests;


import jakarta.validation.constraints.NotNull;

public record UserRequest (
  @NotNull String name,
  @NotNull String password
) {}
