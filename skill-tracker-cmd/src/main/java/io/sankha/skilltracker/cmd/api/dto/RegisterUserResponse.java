package io.sankha.skilltracker.cmd.api.dto;

import io.sankha.skilltracker.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse extends BaseResponse {
  private String id;

  public RegisterUserResponse(String id, String message) {
    super(message);
    this.id = id;
  }
}
