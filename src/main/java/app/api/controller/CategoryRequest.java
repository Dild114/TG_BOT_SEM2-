package app.api.controller;

import app.api.entity.UserId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    @NotNull(message = "Name cannot be null")
    String name,
    @Min(value = 1, message = "UserId cannot be")
    UserId userId
) {}
