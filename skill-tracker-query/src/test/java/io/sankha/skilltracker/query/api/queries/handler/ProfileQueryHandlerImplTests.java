package io.sankha.skilltracker.query.api.queries.handler;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.profile.models.EmployeeEntity;
import io.sankha.skilltracker.query.api.queries.profile.FindAllProfilesQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByIdQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByNameQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileWithSkillQuery;
import io.sankha.skilltracker.query.api.repositories.ProfileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProfileQueryHandlerImplTests {
  @InjectMocks ProfileQueryHandlerImpl queryHandler;

  @Mock private ProfileRepository repository;

  @Test
  void handleFindAllProfilesQueryTest() {
    Iterable<EmployeeEntity> mockList = new ArrayList<>();
    Mockito.when(this.repository.findAll()).thenReturn(mockList);

    var responseDto = this.queryHandler.handle(new FindAllProfilesQuery());
    Assertions.assertNotNull(responseDto);
  }

  @Test
  void handleFindProfileByIdQueryTest() {
    List<BaseEntity> mockList = new ArrayList<>();
    Mockito.when(this.repository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.of(new EmployeeEntity()));

    var responseDto = this.queryHandler.handle(new FindProfileByIdQuery(""));
    Assertions.assertNotNull(responseDto);
  }

  @Test
  void handleFindProfileByNameQueryTest() {
    List<BaseEntity> mockList = new ArrayList<>();
    Mockito.when(this.repository.findByFirstName(ArgumentMatchers.any())).thenReturn(mockList);

    var responseDto = this.queryHandler.handle(new FindProfileByNameQuery(""));
    Assertions.assertNotNull(responseDto);
  }

  @Test
  void handleFindProfileWithSkillQueryTest() {
    List<BaseEntity> mockList = new ArrayList<>();
    Mockito.when(this.repository.findByHtmlCssJavascriptGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByAngularGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByReactGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findBySpringGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByRestfulGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByHibernateGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByGitGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByDockerGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByJenkinsGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByAwsGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findBySpokenGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByCommunicationGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);
    Mockito.when(this.repository.findByAptitudeGreaterThan(ArgumentMatchers.anyInt()))
        .thenReturn(mockList);

    var htmlCssJavascriptDto =
        this.queryHandler.handle(new FindProfileWithSkillQuery("htmlCssJavascript"));
    Assertions.assertNotNull(htmlCssJavascriptDto);
    var angularDto = this.queryHandler.handle(new FindProfileWithSkillQuery("angular"));
    Assertions.assertNotNull(angularDto);
    var reactDto = this.queryHandler.handle(new FindProfileWithSkillQuery("react"));
    Assertions.assertNotNull(reactDto);
    var springDto = this.queryHandler.handle(new FindProfileWithSkillQuery("spring"));
    Assertions.assertNotNull(springDto);
    var restfulDto = this.queryHandler.handle(new FindProfileWithSkillQuery("restful"));
    Assertions.assertNotNull(restfulDto);
    var hibernateDto = this.queryHandler.handle(new FindProfileWithSkillQuery("hibernate"));
    Assertions.assertNotNull(hibernateDto);
    var gitDto = this.queryHandler.handle(new FindProfileWithSkillQuery("git"));
    Assertions.assertNotNull(gitDto);
    var dockerDto = this.queryHandler.handle(new FindProfileWithSkillQuery("docker"));
    Assertions.assertNotNull(dockerDto);
    var jenkinsDto = this.queryHandler.handle(new FindProfileWithSkillQuery("jenkins"));
    Assertions.assertNotNull(jenkinsDto);
    var awsDto = this.queryHandler.handle(new FindProfileWithSkillQuery("aws"));
    Assertions.assertNotNull(awsDto);
    var spokenDto = this.queryHandler.handle(new FindProfileWithSkillQuery("spoken"));
    Assertions.assertNotNull(spokenDto);
    var communicationDto = this.queryHandler.handle(new FindProfileWithSkillQuery("communication"));
    Assertions.assertNotNull(communicationDto);
    var aptitudeDto = this.queryHandler.handle(new FindProfileWithSkillQuery("aptitude"));
    Assertions.assertNotNull(aptitudeDto);
  }

  @Test
  void handleFindProfileWithSkillQueryExceptionTest() {
    var findProfileWithSkillQuery = new FindProfileWithSkillQuery("");
    var thrown =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> this.queryHandler.handle(findProfileWithSkillQuery),
            "Expected handle() to throw, but it didn't");

    Assertions.assertFalse(thrown.getMessage().contains("No suitable skill found!"));
  }
}
