package app.api.controller;


public record UserRequest (
  String name,
  String password) {}
