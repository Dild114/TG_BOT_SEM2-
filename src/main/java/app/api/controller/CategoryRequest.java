package app.api.controller;

import app.api.entity.UserId;

public record CategoryRequest(
    String name,
    UserId userId
) {}
