package io.sankha.skilltracker.user.models;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEntity implements GrantedAuthority {
  READ_PRIVILEGE,
  WRITE_PRIVILEGE,
  ADMIN_PRIVILEGE;

  @Override
  public String getAuthority() {
    return name();
  }
}
