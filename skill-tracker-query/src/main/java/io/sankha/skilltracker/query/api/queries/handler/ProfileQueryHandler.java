package io.sankha.skilltracker.query.api.queries.handler;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.query.api.queries.profile.FindAllProfilesQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByIdQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByNameQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileWithSkillQuery;
import java.util.List;

public interface ProfileQueryHandler {
  List<BaseEntity> handle(FindAllProfilesQuery query);

  List<BaseEntity> handle(FindProfileByIdQuery query);

  List<BaseEntity> handle(FindProfileByNameQuery query);

  List<BaseEntity> handle(FindProfileWithSkillQuery query);
}
