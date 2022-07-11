package io.sankha.skilltracker.cmd.api.security;

public interface PasswordEncoder {
  String hashPassword(String password);
}
