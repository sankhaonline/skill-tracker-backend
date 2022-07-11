package io.sankha.skilltracker.query.api.dto;

import io.sankha.skilltracker.common.dto.BaseResponse;
import io.sankha.skilltracker.user.models.UserEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserLookupResponse extends BaseResponse {
  private List<UserEntity> users;

  public UserLookupResponse(String message) {
    super(message);
  }

  public UserLookupResponse(List<UserEntity> users) {
    this.users = users;
  }

  public UserLookupResponse(String message, UserEntity user) {
    super(message);
    this.users = new ArrayList<>();
    this.users.add(user);
  }

  public UserLookupResponse(UserEntity user) {
    this.users = new ArrayList<>();
    this.users.add(user);
  }
}
