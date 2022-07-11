package io.sankha.skilltracker.user.models;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Component
public class UserEntity implements BaseEntity {
  @Id private String id;

  @NotBlank(message = "First Name is mandatory")
  @Size(min = 5, max = 30, message = "First Name must have within 5 -30 characters")
  private String firstname;

  @NotBlank(message = "Last Name is mandatory")
  @Size(min = 5, max = 30, message = "Last Name must have within 5 -30 characters")
  private String lastname;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String emailAddress;

  @NotBlank(message = "User Name is mandatory")
  @Size(min = 3, max = 10, message = "First Name must have within 3 -10 characters")
  private String username;

  @NotBlank(message = "Password is mandatory")
  private String password;

  @ElementCollection private List<RoleEntity> roles;
}
