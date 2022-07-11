package io.sankha.skilltracker.query;

import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
import io.sankha.skilltracker.query.api.queries.handler.ProfileQueryHandler;
import io.sankha.skilltracker.query.api.queries.handler.UserQueryHandler;
import io.sankha.skilltracker.query.api.queries.profile.FindAllProfilesQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByIdQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByNameQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileWithSkillQuery;
import io.sankha.skilltracker.query.api.queries.user.FindAllUsersQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByIdQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByNameQuery;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EntityScan(
    basePackages = {"io.sankha.skilltracker.user.models", "io.sankha.skilltracker.profile.models"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerQueryApplication {

  private final QueryDispatcher queryDispatcher;

  private final ProfileQueryHandler profileQueryHandler;
  private final UserQueryHandler userQueryHandler;

  public static void main(String... args) {
    SpringApplication.run(SkillTrackerQueryApplication.class, args);
  }

  @PostConstruct
  public void registerHandlers() {
    queryDispatcher.registerHandler(FindAllProfilesQuery.class, profileQueryHandler::handle);
    queryDispatcher.registerHandler(FindProfileByIdQuery.class, profileQueryHandler::handle);
    queryDispatcher.registerHandler(FindProfileByNameQuery.class, profileQueryHandler::handle);
    queryDispatcher.registerHandler(FindProfileWithSkillQuery.class, profileQueryHandler::handle);

    queryDispatcher.registerHandler(FindUserByIdQuery.class, userQueryHandler::handle);
    queryDispatcher.registerHandler(FindAllUsersQuery.class, userQueryHandler::handle);
    queryDispatcher.registerHandler(FindUserByNameQuery.class, userQueryHandler::handle);
  }
}
