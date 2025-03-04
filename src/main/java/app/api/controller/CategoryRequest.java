package app.api.controller;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    @NotNull(message = "Name cannot be null")
    String name
) {}
