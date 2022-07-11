package io.sankha.skilltracker.query.api.queries.profile;

import io.sankha.skilltracker.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindProfileWithSkillQuery implements BaseQuery {
  private String skillName;
}
